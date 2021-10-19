// Copyright 2021 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
///////////////////////////////////////////////////////////////////////////////

package com.google.crypto.tink.hybrid.internal;

import com.google.crypto.tink.proto.HpkeParams;
import java.security.GeneralSecurityException;
import java.util.Arrays;

/**
 * Helper class for creating HPKE primitives from either algorithm identifiers or {@link
 * com.google.crypto.tink.proto.HpkeParams}.
 */
public final class HpkePrimitiveFactory {
  /** Returns an {@link HpkeKem} primitive corresponding to {@code kemId}. */
  public static HpkeKem createKem(byte[] kemId) {
    if (Arrays.equals(kemId, HpkeUtil.X25519_HKDF_SHA256_KEM_ID)) {
      return new X25519HpkeKem("HmacSha256");
    }
    throw new IllegalArgumentException("Unrecognized HPKE KEM identifier");
  }

  /**
   * Returns an {@link HpkeKem} primitive corresponding to {@link
   * com.google.crypto.tink.proto.HpkeParams#getKem()}.
   */
  public static HpkeKem createKem(HpkeParams params) {
    if (params.getKem() == com.google.crypto.tink.proto.HpkeKem.DHKEM_X25519_HKDF_SHA256) {
      return new X25519HpkeKem("HmacSha256");
    }
    throw new IllegalArgumentException("Unrecognized HPKE KEM identifier");
  }

  /** Returns an {@link HpkeKdf} primitive corresponding to {@code kdfId}. */
  public static HpkeKdf createKdf(byte[] kdfId) {
    if (Arrays.equals(kdfId, HpkeUtil.HKDF_SHA256_KDF_ID)) {
      return new HkdfHpkeKdf("HmacSha256");
    }
    throw new IllegalArgumentException("Unrecognized HPKE KDF identifier");
  }

  /**
   * Returns an {@link HpkeKdf} primitive corresponding to {@link
   * com.google.crypto.tink.proto.HpkeParams#getKdf()}.
   */
  public static HpkeKdf createKdf(HpkeParams params) {
    if (params.getKdf() == com.google.crypto.tink.proto.HpkeKdf.HKDF_SHA256) {
      return new HkdfHpkeKdf("HmacSha256");
    }
    throw new IllegalArgumentException("Unrecognized HPKE KDF identifier");
  }

  /** Returns an {@link HpkeAead} primitive corresponding to {@code aeadId}. */
  public static HpkeAead createAead(byte[] aeadId) throws GeneralSecurityException {
    if (Arrays.equals(aeadId, HpkeUtil.AES_128_GCM_AEAD_ID)) {
      return new AesGcmHpkeAead(16);
    } else if (Arrays.equals(aeadId, HpkeUtil.AES_256_GCM_AEAD_ID)) {
      return new AesGcmHpkeAead(32);
    }
    throw new IllegalArgumentException("Unrecognized HPKE AEAD identifier");
  }

  /**
   * Returns an {@link HpkeAead} primitive corresponding to {@link
   * com.google.crypto.tink.proto.HpkeParams#getAead()}.
   */
  public static HpkeAead createAead(HpkeParams params) throws GeneralSecurityException {
    if (params.getAead() == com.google.crypto.tink.proto.HpkeAead.AES_128_GCM) {
      return new AesGcmHpkeAead(16);
    } else if (params.getAead() == com.google.crypto.tink.proto.HpkeAead.AES_256_GCM) {
      return new AesGcmHpkeAead(32);
    }
    throw new IllegalArgumentException("Unrecognized HPKE AEAD identifier");
  }

  private HpkePrimitiveFactory() {}
}
