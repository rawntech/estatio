package org.estatio.dom.lease;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;

import org.estatio.dom.asset.Unit;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "VERSION")
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Discriminator(strategy = DiscriminatorStrategy.CLASS_NAME)
@javax.jdo.annotations.Queries({ @javax.jdo.annotations.Query(name = "units_findUnitsByReference", language = "JDOQL", value = "SELECT  FROM org.estatio.dom.lease.UnitForLease WHERE reference.matches(:r)") })
public class UnitForLease extends Unit {

    @javax.jdo.annotations.Persistent(mappedBy = "unit", defaultFetchGroup = "false")
    private SortedSet<LeaseUnit> leases = new TreeSet<LeaseUnit>();

    @Render(Type.EAGERLY)
    @MemberOrder(sequence = "2.2")
    public SortedSet<LeaseUnit> getLeases() {
        return leases;
    }

    public void setLeases(final SortedSet<LeaseUnit> leases) {
        this.leases = leases;
    }

    public void addToLeases(final LeaseUnit leaseUnit) {
        if (leaseUnit == null || getLeases().contains(leaseUnit)) {
            return;
        }
        leaseUnit.clearUnit();
        leaseUnit.setUnit(this);
        getLeases().add(leaseUnit);
    }

    public void removeFromLeases(final LeaseUnit leaseUnit) {
        if (leaseUnit == null || !getLeases().contains(leaseUnit)) {
            return;
        }
        leaseUnit.setUnit(null);
        getLeases().remove(leaseUnit);
    }

}