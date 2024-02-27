package net.sf.uadetector.internal.data;

import net.sf.uadetector.internal.data.domain.Identifiable;
import net.sf.uadetector.internal.util.CompareNullSafe;

import java.util.Comparator;


public final class IdentifiableComparator
		extends CompareNullSafe<Identifiable>
		implements Comparator<Identifiable>
{

	public static final IdentifiableComparator INSTANCE = new IdentifiableComparator();

	private static final long serialVersionUID = -4279820324904203666L;

	/**
	 * <strong>Attention:</strong> This class is a stateless singleton and not intended to create more than one object
	 * from it.
	 */
	private IdentifiableComparator()
	{
		// This class is not intended to create own objects from it.
	}

	@Override
	public int compareType(Identifiable o1, Identifiable o2)
	{
		return compareInt(o1.getId(), o2.getId());
	}

}
