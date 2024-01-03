package com.guicedee.guicedservlets.undertow.services;

import com.guicedee.guicedinjection.interfaces.*;
import io.undertow.server.*;
import jakarta.annotation.*;

public interface UndertowPathHandler<J extends UndertowPathHandler<J>> extends IDefaultService<J>
{
	HttpHandler registerPathHandler(@Nullable HttpHandler incoming);
}
