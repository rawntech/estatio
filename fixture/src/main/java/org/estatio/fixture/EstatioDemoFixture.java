/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
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
package org.estatio.fixture;

import org.estatio.fixture.asset.PropertiesAndUnitsForKal;
import org.estatio.fixture.asset.PropertiesAndUnitsForOxf;
import org.estatio.fixture.financial.*;
import org.estatio.fixture.invoice.InvoiceAndInvoiceItemForKalPoison001;
import org.estatio.fixture.invoice.InvoiceAndInvoiceItemForOxfPoison003;
import org.estatio.fixture.lease.*;
import org.estatio.fixture.party.*;
import org.apache.isis.applib.fixturescripts.CompositeFixtureScript;

public class EstatioDemoFixture extends CompositeFixtureScript {

    public EstatioDemoFixture() {
        super(null, "demo");
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        execute(new EstatioBaseLineFixture(), executionContext);

        // execute("parties", new PersonsAndOrganisationsAndCommunicationChannelsForAll(), executionContext);
        execute(new OrganisationAndCommunicationChannelsForAcme(), executionContext);
        execute(new OrganisationAndCommunicationChannelsForHelloWorld(), executionContext);
        execute(new OrganisationAndCommunicationChannelsForTopModel(), executionContext);
        execute(new OrganisationAndCommunicationChannelsForMediaX(), executionContext);
        execute(new OrganisationAndCommunicationChannelsForPoison(), executionContext);
        execute(new OrganisationAndCommunicationChannelsForPret(), executionContext);
        execute(new OrganisationAndCommunicationChannelsForMiracle(), executionContext);
        execute(new PersonForJohnDoe(), executionContext);
        execute(new PersonForLinusTorvalds(), executionContext);

        // execute("properties", new PropertiesAndUnitsForAll(), executionContext);
        execute(new PropertiesAndUnitsForOxf(), executionContext);
        execute(new PropertiesAndUnitsForKal(), executionContext);

        // execute("leases", new LeasesEtcForAll(), executionContext);
        execute(new LeasesEtcForOxfTopModel001(), executionContext);
        execute(new LeasesEtcForOxfMediax002(), executionContext);
        execute(new LeasesEtcForOxfPoison003(), executionContext);
        execute(new LeasesEtcForOxfPret004(), executionContext);
        execute(new LeasesEtcForOxfMiracl005(), executionContext);
        execute(new LeasesEtcForKalPoison001(), executionContext);

        //execute("invoices", new InvoicesAndInvoiceItemsForAll(), executionContext);
        execute(new InvoiceAndInvoiceItemForOxfPoison003(), executionContext);
        execute(new InvoiceAndInvoiceItemForKalPoison001(), executionContext);

        //execute("bank-accounts", new BankAccountsAndMandatesForAll(), executionContext);
        execute(new BankAccountAndMandateForAcme(), executionContext);
        execute(new BankAccountAndMandateForHelloWorld(), executionContext);
        execute(new BankAccountAndMandateForMediaX(), executionContext);
        execute(new BankAccountAndMandateForMiracle(), executionContext);
        execute(new BankAccountAndMandateForPoison(), executionContext);
        execute(new BankAccountAndMandateForPret(), executionContext);
        execute(new BankAccountAndMandateForTopModel(), executionContext);

    }
}