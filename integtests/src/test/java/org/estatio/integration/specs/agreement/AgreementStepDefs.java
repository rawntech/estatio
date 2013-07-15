/*
 *  Copyright 2012-2013 Eurocommercial Properties NV
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.estatio.integration.specs.agreement;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import com.google.common.collect.Lists;

import cucumber.api.Transform;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;

import org.jmock.Expectations;
import org.joda.time.LocalDate;

import org.apache.isis.core.specsupport.scenarios.InMemoryDB;
import org.apache.isis.core.specsupport.scenarios.ScenarioExecutionScope;
import org.apache.isis.core.specsupport.specs.CukeStepDefsAbstract;
import org.apache.isis.core.specsupport.specs.V;

import org.estatio.dom.agreement.AgreementRole;
import org.estatio.dom.agreement.AgreementRoleType;
import org.estatio.dom.agreement.AgreementRoleTypes;
import org.estatio.dom.lease.Lease;
import org.estatio.dom.lease.Leases;
import org.estatio.dom.party.Parties;
import org.estatio.dom.party.Party;
import org.estatio.dom.party.PartyForTesting;
import org.estatio.fixture.EstatioTransactionalObjectsFixture;
import org.estatio.integration.specs.ERD;
import org.estatio.integration.specs.ETO;

public class AgreementStepDefs extends CukeStepDefsAbstract {

    // //////////////////////////////////////
    
    @Before({"@unit"})
    public void beforeScenarioUnitScope() {
        before(ScenarioExecutionScope.UNIT);
    }

    @Before({"@integration"})
    public void beforeScenarioIntegrationScope() {
        before(ScenarioExecutionScope.INTEGRATION);
    }

    @After
    public void afterScenario(cucumber.api.Scenario sc) {
        assertMocksSatisfied();
        after(sc);
    }

    // //////////////////////////////////////

    @Before(value={"@unit"}, order=20000)
    public void unitFixtures() throws Throwable {
        final InMemoryDB inMemoryDB = new InMemoryDBForEstatio(this.scenarioExecution());
        checking(new Expectations() {
            {
                allowing(service(Leases.class)).findLeaseByReference(with(any(String.class)));
                will(inMemoryDB.finds(Lease.class));
                
                allowing(service(Parties.class)).findPartyByReferenceOrName(with(any(String.class)));
                will(inMemoryDB.finds(PartyForTesting.class));
                
                allowing(service(AgreementRoleTypes.class)).findByTitle(with(any(String.class)));
                will(inMemoryDB.finds(AgreementRoleType.class));
            }
        });
    }

    @Before(value={"@integration"}, order=20000)
    public void integrationFixtures() throws Throwable {
        scenarioExecution().install(new EstatioTransactionalObjectsFixture());
    }
    
    // //////////////////////////////////////
    
    @Given(".*there is.* lease \"([^\"]*)\"$")
    public void given_lease(final String leaseReference) throws Throwable {
        final Lease lease = service(Leases.class).findLeaseByReference(leaseReference);
        putVar("lease", leaseReference, lease);
    }

    
    @Given(".*there is.* party \"([^\"]*)\"$")
    public void given_party(final String partyReference) throws Throwable {
        final Party party = service(Parties.class).findPartyByReferenceOrName(partyReference);
        putVar("party", partyReference, party);
    }

    @Given(".*lease has no.* roles$")
    public void given_lease_has_no_roles() throws Throwable {
        Lease lease = getVar("lease", null, Lease.class);
        assertThat(lease.getRoles().size(), equalTo(0));
    }

    @Given("^the lease.* roles collection contains:$")
    public void given_the_lease_s_roles_collection_contains(final List<AgreementRoleDesc> listOfActuals) throws Throwable {
        final Lease lease = getVar("lease", null, Lease.class);
        assertThat(lease.getRoles().isEmpty(), is(true));
        for (AgreementRoleDesc ard : listOfActuals) {
            lease.addRole(ard.party, ard.type, ard.startDate, ard.endDate);
        }
    }

    // //////////////////////////////////////

    
    @When("^.* add.* agreement role.*type \"([^\"]*)\".* start date \"([^\"]*)\".* end date \"([^\"]*)\".* this party$")
    public void when_add_agreement_role_with_type_with_start_date_and_end_date(
            @Transform(ERD.AgreementRoleType.class) final AgreementRoleType type, 
            @Transform(V.LyyyyMMdd.class) final LocalDate startDate, 
            @Transform(V.LyyyyMMdd.class) final LocalDate endDate) throws Throwable {
      
        final Lease lease = getVar("lease", null, Lease.class);
        final Party party = getVar("party", null, Party.class);

        wrap(lease).addRole(party, type, startDate, endDate);
    }
    
    // //////////////////////////////////////

    @Then("^.*lease's roles collection should contain:$")
    public void then_leases_roles_collection_should_contain(
            final List<AgreementRoleDesc> listOfExpecteds) throws Throwable {
        final Lease lease = getVar("lease", null, Lease.class);
        
        final SortedSet<AgreementRole> roles = lease.getRoles();
        final ArrayList<AgreementRole> rolesList = Lists.newArrayList(roles);
        assertTableEquals(listOfExpecteds, rolesList);
    }

    public static class AgreementRoleDesc {
        @XStreamConverter(ERD.AgreementRoleType.class) private AgreementRoleType type;
        @XStreamConverter(V.LyyyyMMdd.class) private LocalDate startDate;
        @XStreamConverter(V.LyyyyMMdd.class) private LocalDate endDate;
        @XStreamConverter(ETO.Lease.class) private Lease agreement;
        @XStreamConverter(ETO.Party.class) private Party party;
    }
    
}