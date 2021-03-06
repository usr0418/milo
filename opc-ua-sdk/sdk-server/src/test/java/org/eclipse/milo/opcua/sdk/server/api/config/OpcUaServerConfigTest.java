/*
 * Copyright (c) 2019 the Eclipse Milo Authors
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.eclipse.milo.opcua.sdk.server.api.config;

import java.io.IOException;

import com.google.common.io.Files;
import org.eclipse.milo.opcua.sdk.server.identity.AnonymousIdentityValidator;
import org.eclipse.milo.opcua.stack.core.security.DefaultCertificateManager;
import org.eclipse.milo.opcua.stack.core.security.DefaultCertificateValidator;
import org.eclipse.milo.opcua.stack.core.security.DefaultTrustListManager;
import org.eclipse.milo.opcua.stack.core.types.builtin.DateTime;
import org.eclipse.milo.opcua.stack.core.types.structured.BuildInfo;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class OpcUaServerConfigTest {

    @Test
    public void testCopy() throws IOException {
        DefaultTrustListManager trustListManager = new DefaultTrustListManager(Files.createTempDir());

        OpcUaServerConfig original = OpcUaServerConfig.builder()
            .setCertificateManager(new DefaultCertificateManager())
            .setTrustListManager(trustListManager)
            .setCertificateValidator(new DefaultCertificateValidator(trustListManager))
            .setIdentityValidator(AnonymousIdentityValidator.INSTANCE)
            .setBuildInfo(new BuildInfo("a", "b", "c", "d", "e", DateTime.MIN_VALUE))
            .setLimits(new OpcUaServerConfigLimits() {})
            .build();

        OpcUaServerConfig copy = OpcUaServerConfig.copy(original).build();

        assertEquals(copy.getIdentityValidator(), original.getIdentityValidator());
        assertEquals(copy.getBuildInfo(), original.getBuildInfo());
        assertEquals(copy.getLimits(), original.getLimits());
    }

}
