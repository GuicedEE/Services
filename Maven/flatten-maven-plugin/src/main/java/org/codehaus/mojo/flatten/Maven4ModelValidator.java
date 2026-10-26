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
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.validation.ModelValidator;

/**
 * Maven 4 has already validated the build model. Its legacy model builder still rejects
 * the 4.1.0 version while the Flatten plugin rebuilds that model using its custom
 * dependency inheritance and profile rules. Validate a compatibility view without
 * altering the model used by those transformations or disabling other validation.
 */
final class Maven4ModelValidator implements ModelValidator {
    private final ModelValidator delegate;

    Maven4ModelValidator(ModelValidator delegate) {
        this.delegate = delegate;
    }

    private Model compatibilityView(Model model) {
        if (!"4.1.0".equals(model.getModelVersion())) {
            return model;
        }
        Model view = model.clone();
        view.setModelVersion("4.0.0");
        return view;
    }

    @Override
    public void validateRawModel(Model model, ModelBuildingRequest request, ModelProblemCollector problems) {
        delegate.validateRawModel(compatibilityView(model), request, problems);
    }

    @Override
    public void validateEffectiveModel(Model model, ModelBuildingRequest request, ModelProblemCollector problems) {
        delegate.validateEffectiveModel(compatibilityView(model), request, problems);
    }
}
