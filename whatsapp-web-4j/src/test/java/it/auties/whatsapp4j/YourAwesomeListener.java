package it.auties.whatsapp4j;

import it.auties.whatsapp4j.api.WhatsappAPI;
import it.auties.whatsapp4j.builder.WhatsappMediaMessageBuilder;
import it.auties.whatsapp4j.listener.RegisterListener;
import it.auties.whatsapp4j.listener.WhatsappListener;
import it.auties.whatsapp4j.manager.WhatsappDataManager;
import it.auties.whatsapp4j.model.*;
import it.auties.whatsapp4j.response.impl.UserInformationResponse;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RegisterListener
public class YourAwesomeListener implements WhatsappListener
{
	private final WhatsappAPI api;
	private final WhatsappDataManager whatsappDataManager = WhatsappDataManager.singletonInstance();
	
	public YourAwesomeListener(WhatsappAPI api)
	{
		this.api = api;
	}
	
	public void onLoggedIn(UserInformationResponse info, boolean firstLogin)
	{
		System.out.println("Connected :)");
	}
	
	@Override
	public void onContactsReceived()
	{
		System.out.println("received contacts");
		for (WhatsappContact contact : whatsappDataManager
				.contacts())
		{
			System.out.println("WhatsApp Contact Found : " + contact);
		}
		
		Optional<WhatsappContact> c1 = whatsappDataManager
				.findContactByName("Contact 1");
		Optional<WhatsappContact> c2 = whatsappDataManager
				.findContactByName("Contact 2");
		Optional<WhatsappContact> c3 = whatsappDataManager
				.findContactByName("Contact 3");
		
		WhatsappContact[] whatsappContacts = List.of(c1.get(), c2.get(), c3.get())
		                                         .toArray(new WhatsappContact[]{});
		
		WhatsappChat groupChat = null;
		for (WhatsappChat chat : whatsappDataManager.chats())
		{
			if (chat.name()
			        .equals("Chat 1"))
			{
				groupChat = chat;
				break;
			}
		}
		
		if (groupChat == null)
		{
			CompletableFuture<WhatsappChat> groupCreateFuture = api.createGroup("Chat 1", whatsappContacts);
			try
			{
				groupChat = groupCreateFuture.get();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			catch (ExecutionException e)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			byte[] logoBytes = null;
			byte[] documentBytes = null;
			try(FileInputStream fis = new FileInputStream(new File("c:/temp/pic.png")))
			{
				logoBytes = IOUtils.toByteArray(fis);
			}
			try(FileInputStream fis = new FileInputStream(new File("c:/temp/doc.pdf")))
			{
				documentBytes = IOUtils.toByteArray(fis);
			}
			
			var logo = WhatsappMediaMessage.newMediaMessage()
			                               .type(WhatsappMediaMessageType.IMAGE)
			                               .caption("Caption")
			                               .media(logoBytes)
			                               .chat(groupChat)
			                               .create();
			var text = WhatsappTextMessage
					.newTextMessage(groupChat, "Quick Text 1");
			var text2 = WhatsappTextMessage
					.newTextMessage(groupChat, "Quick Text 2");
			var doc = WhatsappMediaMessage.newMediaMessage()
			                              .type(WhatsappMediaMessageType.DOCUMENT)
			                              .chat(groupChat)
			                              .media(documentBytes)
			                              .create();
			api.sendMessage(logo);
			api.sendMessage(text);
			api.sendMessage(text2);
			api.sendMessage(doc);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onChatReceived(WhatsappChat chat)
	{
		System.out.println("Chat Received : " + chat);
		WhatsappListener.super.onChatReceived(chat);
	}
	
	@Override
	public void onChatsReceived()
	{
		System.out.println("Chats loaded");
		for (WhatsappChat chat : whatsappDataManager
				.chats())
		{
			System.out.println("chat : " + chat);
		}
		
		WhatsappListener.super.onChatsReceived();
	}
	
	@Override
	public void onGroupAction(WhatsappChat group, WhatsappContact participant, WhatsappGroupAction action)
	{
		System.out.println("Group Action : " + group);
		System.out.println("Group Part : " + participant);
		System.out.println("Group ACC" + action);
		WhatsappListener.super.onGroupAction(group, participant, action);
	}
	
	@Override
	public void onMessageDeleted(WhatsappChat chat, WhatsappUserMessage message, boolean everyone)
	{
		System.out.println("Message Deleted - " + chat);
		System.out.println("Message Deleted Chat - " + message + " - everyone - " + everyone);
		WhatsappListener.super.onMessageDeleted(chat, message, everyone);
	}
	
	public void onDisconnected()
	{
		System.out.println("Disconnected :(");
	}
}