package org.codehaus.mojo.flatten;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.model.Model;
import org.apache.maven.model.building.DefaultModelBuildingRequest;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.validation.ModelValidator;
import org.junit.Test;

import static org.junit.Assert.*;

public class Maven4ModelValidatorTest {
    @Test
    public void validatesFourOneWithoutMutatingSource() {
        final Model[] seen = new Model[2];
        ModelValidator delegate = new ModelValidator() {
            public void validateRawModel(Model m, ModelBuildingRequest r, ModelProblemCollector p) {
                seen[0] = m;
            }

            public void validateEffectiveModel(Model m, ModelBuildingRequest r, ModelProblemCollector p) {
                seen[1] = m;
            }
        };
        Maven4ModelValidator validator = new Maven4ModelValidator(delegate);
        Model model = new Model();
        model.setModelVersion("4.1.0");
        model.setArtifactId("fixture");
        validator.validateRawModel(model, new DefaultModelBuildingRequest(), null);
        validator.validateEffectiveModel(model, new DefaultModelBuildingRequest(), null);
        for (Model view : seen) {
            assertNotSame(model, view);
            assertEquals("4.0.0", view.getModelVersion());
            assertEquals("fixture", view.getArtifactId());
        }
        assertEquals("4.1.0", model.getModelVersion());
    }

    @Test
    public void leavesOtherModelVersionsToTheDelegate() {
        for (String version : new String[] {"4.0.0", "4.2.0"}) {
            final Model[] seen = new Model[1];
            ModelValidator delegate = new ModelValidator() {
                public void validateRawModel(Model m, ModelBuildingRequest r, ModelProblemCollector p) {
                    seen[0] = m;
                }

                public void validateEffectiveModel(Model m, ModelBuildingRequest r, ModelProblemCollector p) {
                    seen[0] = m;
                }
            };
            Model model = new Model();
            model.setModelVersion(version);
            new Maven4ModelValidator(delegate).validateRawModel(model, new DefaultModelBuildingRequest(), null);
            assertSame(model, seen[0]);
            assertEquals(version, seen[0].getModelVersion());
        }
    }

    @Test
    public void stillRejectsInvalidModelContent() {
        org.apache.maven.model.validation.DefaultModelValidator delegate =
                new org.apache.maven.model.validation.DefaultModelValidator(
                        new org.apache.maven.model.interpolation.DefaultModelVersionProcessor());
        java.util.List<org.apache.maven.model.building.ModelProblemCollectorRequest> problems =
                new java.util.ArrayList<>();
        Model model = new Model();
        model.setModelVersion("4.1.0");
        model.setGroupId("fixture");
        model.setArtifactId("invalid artifact");
        model.setVersion("1");
        new Maven4ModelValidator(delegate)
                .validateEffectiveModel(
                        model,
                        new DefaultModelBuildingRequest()
                                .setValidationLevel(ModelBuildingRequest.VALIDATION_LEVEL_MAVEN_3_1),
                        problems::add);
        assertTrue(problems.stream().anyMatch(p -> p.getMessage().contains("artifactId")));
        assertEquals("4.1.0", model.getModelVersion());
    }
}
