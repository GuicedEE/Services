/*-------------------------------------------------------------------------
|   RXTX License v 2.1 - LGPL v 2.1 + Linking Over Controlled Interface.
|   RXTX is a native interface to serial ports in java.
|   Copyright 1997-2007 by Trent Jarvi tjarvi@qbang.org and others who
|   actually wrote it.  See individual source files for more information.
|
|   A copy of the LGPL v 2.1 may be found at
|   http://www.gnu.org/licenses/lgpl.txt on March 4th 2007.  A copy is
|   here for your convenience.
|
|   This library is free software; you can redistribute it and/or
|   modify it under the terms of the GNU Lesser General Public
|   License as published by the Free Software Foundation; either
|   version 2.1 of the License, or (at your option) any later version.
|
|   This library is distributed in the hope that it will be useful,
|   but WITHOUT ANY WARRANTY; without even the implied warranty of
|   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
|   Lesser General Public License for more details.
|
|   An executable that contains no derivative of any portion of RXTX, but
|   is designed to work with RXTX by being dynamically linked with it,
|   is considered a "work that uses the Library" subject to the terms and
|   conditions of the GNU Lesser General Public License.
|
|   The following has been added to the RXTX License to remove
|   any confusion about linking to RXTX.   We want to allow in part what
|   section 5, paragraph 2 of the LGPL does not permit in the special
|   case of linking over a controlled interface.  The intent is to add a
|   Java Specification Request or standards body defined interface in the
|   future as another exception but one is not currently available.
|
|   http://www.fsf.org/licenses/gpl-faq.html#LinkingOverControlledInterface
|
|   As a special exception, the copyright holders of RXTX give you
|   permission to link RXTX with independent modules that communicate with
|   RXTX solely through the Sun Microsytems CommAPI interface version 2,
|   regardless of the license terms of these independent modules, and to copy
|   and distribute the resulting combined work under terms of your choice,
|   provided that every copy of the combined work is accompanied by a complete
|   copy of the source code of RXTX (the version of RXTX used to produce the
|   combined work), being distributed under the terms of the GNU Lesser General
|   Public License plus this exception.  An independent module is a
|   module which is not derived from or based on RXTX.
|
|   Note that people who make modified versions of RXTX are not obligated
|   to grant this special exception for their modified versions; it is
|   their choice whether to do so.  The GNU Lesser General Public License
|   gives permission to release a modified version without this exception; this
|   exception also makes it possible to release a modified version which
|   carries forward this exception.
|
|   You should have received a copy of the GNU Lesser General Public
|   License along with this library; if not, write to the Free
|   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
|   All trademarks belong to their respective owners.
--------------------------------------------------------------------------*/
package gnu.io;

import com.fasterxml.jackson.annotation.*;
import gnu.io.factory.*;

import java.io.*;
import java.util.*;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, setterVisibility = NONE)
public class NRSerialPort<J extends NRSerialPort<J>> implements Serializable
{
	@SuppressWarnings("MissingSerialAnnotation")
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private transient RXTXPort serial;
	
	private String port = null;
	private boolean connected = false;
	private int baud = 115200;
	
	public NRSerialPort()
	{
		//no config
	}
	
	/**
	 * Class Constructor for a NRSerialPort with a given port and baudrate.
	 *
	 * @param port the port to connect to (i.e. COM6 or /dev/ttyUSB0)
	 * @param baud the baudrate to use (i.e. 9600 or 115200)
	 */
	public NRSerialPort(String port, int baud)
	{
		setPort(port);
		setBaud(baud);
	}
	
	/**
	 * Attempts to connect
	 *
	 * @return
	 * @throws gnu.io.NRSerialPortException if cannot connect
	 */
	public boolean connect()
	{
		if (isConnected())
		{
			System.err.println(port + " is already connected.");
			return true;
		}
		
		try
		{
			serial = new RxTxPortCreator().createPort(port);
			serial.setSerialPortParams(getBaud(), SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			setConnected(true);
		}
		catch (NativeResourceException e)
		{
			setConnected(false);
			throw new NRSerialPortException("No Port", e);
		}
		catch (PortInUseException e)
		{
			setConnected(false);
			throw new NRSerialPortException("Port in Use", e);
		}
		catch (Exception e)
		{
			setConnected(false);
			throw new NRSerialPortException("General Exception - " + e.getMessage(), e);
		}
		
		if (isConnected())
		{
			serial.notifyOnDataAvailable(true);
		}
		return isConnected();
	}
	
	@JsonIgnore
	public InputStream getInputStream()
	{
		return serial.getInputStream();
	}
	
	@JsonIgnore
	public OutputStream getOutputStream()
	{
		return serial.getOutputStream();
	}
	
	
	/**
	 * Set the port to use (i.e. COM6 or /dev/ttyUSB0)
	 *
	 * @param port the serial port to use
	 */
	public void setPort(String port)
	{
		this.port = port;
	}
	
	
	@SuppressWarnings("unchecked")
	public J disconnect()
	{
		if (!connected)
		{
			return (J)this;
		}
		try
		{
			try
			{
				getInputStream().close();
				getOutputStream().close();
				serial.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			serial = null;
			setConnected(false);
		}
		catch (UnsatisfiedLinkError e)
		{
			throw new NativeResourceException(e.getMessage());
		}
		return (J) this;
	}
	
	public static Set<String> getAvailableSerialPorts()
	{
		Set<String> available = new HashSet<>();
		try
		{
			RXTXCommDriver d = new RXTXCommDriver();
			Set<String> av = d.getPortIdentifiers();
			ArrayList<String> strs = new ArrayList<>();
			for (String s : av)
			{
				strs.add(0, s);
			}
			for (String s : strs)
			{
				available.add(s);
			}
		}
		catch (UnsatisfiedLinkError e)
		{
			e.printStackTrace();
			throw new NativeResourceException(e.getMessage());
		}
		
		return available;
	}

	public static Set<String> getAllWindowsPorts()
	{
		Set<String> available = new HashSet<>();
		try
		{
			RXTXCommDriver d = new RXTXCommDriver();
			Set<String> av = new HashSet<>(Arrays.asList(d.windowsGetSerialPortsFromRegistry()));
			ArrayList<String> strs = new ArrayList<>();
			for (String s : av)
			{
				strs.add(0, s);
			}
			for (String s : strs)
			{
				available.add(s);
			}
		}
		catch (UnsatisfiedLinkError e)
		{
			e.printStackTrace();
			throw new NativeResourceException(e.getMessage());
		}
        catch (Exception e)
        {
			e.printStackTrace();
			throw new NativeResourceException(e.getMessage());
        }

        return available;
	}
	
	
	public boolean isConnected()
	{
		return connected;
	}
	
	
	@SuppressWarnings("unchecked")
	public J setConnected(boolean connected)
	{
		if (this.connected == connected)
		{
			return (J)this;
		}
		this.connected = connected;
		return (J)this;
	}
	
	
	@SuppressWarnings("unchecked")
	public J setBaud(int baud)
	{
		
		this.baud = baud;
		return (J)this;
		
	}
	
	
	public int getBaud()
	{
		return baud;
	}
	
	/**
	 * Enables RS485 half-duplex bus communication for Linux. The Linux kernel uses the RTS pin as bus enable. If you use a device that is configured via the Linux
	 * device tree, take care to add "uart-has-rtscts" and to configure the RTS GPIO correctly.
	 * <p>
	 * Before enabling RS485, the serial port must be connected/opened.
	 * <p>
	 * See also:
	 * <ul>
	 * <li>https://www.kernel.org/doc/Documentation/serial/serial-rs485.txt
	 * <li>https://www.kernel.org/doc/Documentation/devicetree/bindings/serial/serial.txt
	 * </ul>
	 *
	 * @param busEnableActiveLow         true, if the bus enable signal (RTS) shall be low during transmission
	 * @param delayBusEnableBeforeSendMs delay of bus enable signal (RTS) edge to first data edge in ms (not supported by all serial drivers)
	 * @param delayBusEnableAfterSendMs  delay of bus enable signal (RTS) edge after end of transmission in ms (not supported by all serial drivers)
	 * @return the ioctl() return value
	 */
	public int enableRs485(boolean busEnableActiveLow, int delayBusEnableBeforeSendMs, int delayBusEnableAfterSendMs)
	{
		if (serial == null)
		{
			return -1;
		}
		
		return serial.enableRs485(busEnableActiveLow, delayBusEnableBeforeSendMs, delayBusEnableAfterSendMs);
	}
	
	public void notifyOnDataAvailable(boolean b)
	{
		serial.notifyOnDataAvailable(b);
	}
	
	
	public void addEventListener(SerialPortEventListener lsnr) throws TooManyListenersException
	{
		serial.addEventListener(lsnr);
	}
	
	
	public void removeEventListener()
	{
		serial.removeEventListener();
	}
	
	/**
	 * Gets the {@link SerialPort} instance.
	 * This will return null until {@link #connect()} is successfully called.
	 *
	 * @return The {@link SerialPort} instance or null.
	 */
	@JsonIgnore
	public RXTXPort getSerialPortInstance()
	{
		return serial;
	}
	
}
