package com.guicedee.guicedservlets.servlets.services;

import jakarta.validation.constraints.NotNull;

import java.util.Comparator;

public interface IGuiceSiteBinder<M extends com.google.inject.servlet.ServletModule & IGuiceSiteBinder<M>>
				extends Comparable<M>, Comparator<M>
{
	
	@Override
	default int compare(M o1, M o2)
	{
		if (o1 == null || o2 == null)
		{
			return -1;
		}
		return o1.sortOrder()
						.compareTo(o2.sortOrder());
	}
	
	default Integer sortOrder()
	{
		return 100;
	}
	
	@Override
	default int compareTo(@NotNull M o)
	{
		int sort = sortOrder().compareTo(o.sortOrder());
		if (sort == 0)
		{
			return -1;
		}
		return sort;
	}
}
