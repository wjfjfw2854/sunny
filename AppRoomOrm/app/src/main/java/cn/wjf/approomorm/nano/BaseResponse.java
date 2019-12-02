// Generated by the protocol buffer compiler.  DO NOT EDIT!

package cn.wjf.approomorm.nano;

@SuppressWarnings("hiding")
public interface BaseResponse {

  public static final class Base_Response extends
      com.google.protobuf.nano.MessageNano {

    public static final class Result extends
        com.google.protobuf.nano.MessageNano {

      private static volatile Result[] _emptyArray;
      public static Result[] emptyArray() {
        // Lazily initializes the empty array
        if (_emptyArray == null) {
          synchronized (
              com.google.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
            if (_emptyArray == null) {
              _emptyArray = new Result[0];
            }
          }
        }
        return _emptyArray;
      }

      private int bitField0_;

      // optional int32 code = 1;
      private int code_;
      public int getCode() {
        return code_;
      }
      public Result setCode(int value) {
        code_ = value;
        bitField0_ |= 0x00000001;
        return this;
      }
      public boolean hasCode() {
        return ((bitField0_ & 0x00000001) != 0);
      }
      public Result clearCode() {
        code_ = 0;
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      // optional string msg = 2;
      private String msg_;
      public String getMsg() {
        return msg_;
      }
      public Result setMsg(String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        msg_ = value;
        bitField0_ |= 0x00000002;
        return this;
      }
      public boolean hasMsg() {
        return ((bitField0_ & 0x00000002) != 0);
      }
      public Result clearMsg() {
        msg_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      public Result() {
        clear();
      }

      public Result clear() {
        bitField0_ = 0;
        code_ = 0;
        msg_ = "";
        cachedSize = -1;
        return this;
      }

      @Override
      public void writeTo(com.google.protobuf.nano.CodedOutputByteBufferNano output)
          throws java.io.IOException {
        if (((bitField0_ & 0x00000001) != 0)) {
          output.writeInt32(1, code_);
        }
        if (((bitField0_ & 0x00000002) != 0)) {
          output.writeString(2, msg_);
        }
        super.writeTo(output);
      }

      @Override
      protected int computeSerializedSize() {
        int size = super.computeSerializedSize();
        if (((bitField0_ & 0x00000001) != 0)) {
          size += com.google.protobuf.nano.CodedOutputByteBufferNano
              .computeInt32Size(1, code_);
        }
        if (((bitField0_ & 0x00000002) != 0)) {
          size += com.google.protobuf.nano.CodedOutputByteBufferNano
              .computeStringSize(2, msg_);
        }
        return size;
      }

      @Override
      public Result mergeFrom(
              com.google.protobuf.nano.CodedInputByteBufferNano input)
          throws java.io.IOException {
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              return this;
            default: {
              if (!com.google.protobuf.nano.WireFormatNano.parseUnknownField(input, tag)) {
                return this;
              }
              break;
            }
            case 8: {
              code_ = input.readInt32();
              bitField0_ |= 0x00000001;
              break;
            }
            case 18: {
              msg_ = input.readString();
              bitField0_ |= 0x00000002;
              break;
            }
          }
        }
      }

      public static Result parseFrom(byte[] data)
          throws com.google.protobuf.nano.InvalidProtocolBufferNanoException {
        return com.google.protobuf.nano.MessageNano.mergeFrom(new Result(), data);
      }

      public static Result parseFrom(
              com.google.protobuf.nano.CodedInputByteBufferNano input)
          throws java.io.IOException {
        return new Result().mergeFrom(input);
      }
    }

    private static volatile Base_Response[] _emptyArray;
    public static Base_Response[] emptyArray() {
      // Lazily initializes the empty array
      if (_emptyArray == null) {
        synchronized (
            com.google.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
          if (_emptyArray == null) {
            _emptyArray = new Base_Response[0];
          }
        }
      }
      return _emptyArray;
    }

    // optional .Base_Response.Result result = 1;
    public Result result;

    // optional .google.protobuf.Any detail = 2;
    public Any detail;

    public Base_Response() {
      clear();
    }

    public Base_Response clear() {
      result = null;
      detail = null;
      cachedSize = -1;
      return this;
    }

    @Override
    public void writeTo(com.google.protobuf.nano.CodedOutputByteBufferNano output)
        throws java.io.IOException {
      if (this.result != null) {
        output.writeMessage(1, this.result);
      }
      if (this.detail != null) {
        output.writeMessage(2, this.detail);
      }
      super.writeTo(output);
    }

    @Override
    protected int computeSerializedSize() {
      int size = super.computeSerializedSize();
      if (this.result != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeMessageSize(1, this.result);
      }
      if (this.detail != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeMessageSize(2, this.detail);
      }
      return size;
    }

    @Override
    public Base_Response mergeFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      while (true) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            return this;
          default: {
            if (!com.google.protobuf.nano.WireFormatNano.parseUnknownField(input, tag)) {
              return this;
            }
            break;
          }
          case 10: {
            if (this.result == null) {
              this.result = new Result();
            }
            input.readMessage(this.result);
            break;
          }
          case 18: {
            if (this.detail == null) {
              this.detail = new Any();
            }
            input.readMessage(this.detail);
            break;
          }
        }
      }
    }

    public static Base_Response parseFrom(byte[] data)
        throws com.google.protobuf.nano.InvalidProtocolBufferNanoException {
      return com.google.protobuf.nano.MessageNano.mergeFrom(new Base_Response(), data);
    }

    public static Base_Response parseFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      return new Base_Response().mergeFrom(input);
    }
  }
}