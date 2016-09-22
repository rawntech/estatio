/*
 *  Copyright 2016 Eurocommercial Properties NV
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
package org.estatio.fixturescripts;

import org.apache.isis.applib.fixturescripts.DiscoverableFixtureScript;

import org.estatio.fixture.documents.DocumentTypeAndTemplateFSForBlank;
import org.estatio.fixture.documents.DocumentTypeAndTemplateFSForDemoSsrsOnProperty;
import org.estatio.fixture.documents.DocumentTypeAndTemplateFSForHelloGlobal;
import org.estatio.fixture.documents.DocumentTypeAndTemplateForFloorPlanDocumentForGbOxfFS;
import org.estatio.fixture.documents.DocumentTypeAndTemplatesFSForInvoicesUsingSsrs;

public class DemoDocumentAndCommsFixture extends DiscoverableFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new DocumentTypeAndTemplateFSForHelloGlobal());
        executionContext.executeChild(this, new DocumentTypeAndTemplateFSForBlank());
        executionContext.executeChild(this, new DocumentTypeAndTemplateForFloorPlanDocumentForGbOxfFS());
        executionContext.executeChild(this, new DocumentTypeAndTemplatesFSForInvoicesUsingSsrs());
        executionContext.executeChild(this, new DocumentTypeAndTemplateFSForDemoSsrsOnProperty());


    }


}