// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: packets.proto at 41:1
package au.com.codeka.warworlds.common.proto;

import com.squareup.wire.FieldEncoding;
import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;
import com.squareup.wire.WireField;
import com.squareup.wire.internal.Internal;
import java.io.IOException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.List;
import okio.ByteString;

/**
 * Sent from the server when a Star is updated. You can get more than one star update in a packet.
 */
public final class StarUpdatedPacket extends Message<StarUpdatedPacket, StarUpdatedPacket.Builder> {
  public static final ProtoAdapter<StarUpdatedPacket> ADAPTER = new ProtoAdapter_StarUpdatedPacket();

  private static final long serialVersionUID = 0L;

  @WireField(
      tag = 1,
      adapter = "au.com.codeka.warworlds.common.proto.Star#ADAPTER",
      label = WireField.Label.REPEATED
  )
  public final List<Star> stars;

  public StarUpdatedPacket(List<Star> stars) {
    this(stars, ByteString.EMPTY);
  }

  public StarUpdatedPacket(List<Star> stars, ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.stars = Internal.immutableCopyOf("stars", stars);
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.stars = Internal.copyOf("stars", stars);
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof StarUpdatedPacket)) return false;
    StarUpdatedPacket o = (StarUpdatedPacket) other;
    return Internal.equals(unknownFields(), o.unknownFields())
        && Internal.equals(stars, o.stars);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + (stars != null ? stars.hashCode() : 1);
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (stars != null) builder.append(", stars=").append(stars);
    return builder.replace(0, 2, "StarUpdatedPacket{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<StarUpdatedPacket, Builder> {
    public List<Star> stars;

    public Builder() {
      stars = Internal.newMutableList();
    }

    public Builder stars(List<Star> stars) {
      Internal.checkElementsNotNull(stars);
      this.stars = stars;
      return this;
    }

    @Override
    public StarUpdatedPacket build() {
      return new StarUpdatedPacket(stars, buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_StarUpdatedPacket extends ProtoAdapter<StarUpdatedPacket> {
    ProtoAdapter_StarUpdatedPacket() {
      super(FieldEncoding.LENGTH_DELIMITED, StarUpdatedPacket.class);
    }

    @Override
    public int encodedSize(StarUpdatedPacket value) {
      return Star.ADAPTER.asRepeated().encodedSizeWithTag(1, value.stars)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, StarUpdatedPacket value) throws IOException {
      if (value.stars != null) Star.ADAPTER.asRepeated().encodeWithTag(writer, 1, value.stars);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public StarUpdatedPacket decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.stars.add(Star.ADAPTER.decode(reader)); break;
          default: {
            FieldEncoding fieldEncoding = reader.peekFieldEncoding();
            Object value = fieldEncoding.rawProtoAdapter().decode(reader);
            builder.addUnknownField(tag, fieldEncoding, value);
          }
        }
      }
      reader.endMessage(token);
      return builder.build();
    }

    @Override
    public StarUpdatedPacket redact(StarUpdatedPacket value) {
      Builder builder = value.newBuilder();
      Internal.redactElements(builder.stars, Star.ADAPTER);
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}
