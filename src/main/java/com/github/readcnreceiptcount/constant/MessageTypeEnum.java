package com.github.readcnreceiptcount.constant;

public enum MessageTypeEnum {
    MESSAGE_TYPE_CEB312("</CEB312Message>"),
    MESSAGE_TYPE_CEB622("</CEB622Message>"),
    MESSAGE_TYPE_CEB624("</CEB624Message>"),
    MESSAGE_TYPE_CEB626("</CEB626Message>"),
    MESSAGE_TYPE_CEB816("</CEB816Message>"),
    MESSAGE_TYPE_CEB818("</CEB818Message>"),
    MESSAGE_TYPE_CEB512("</CEB512Message>"),
    MESSAGE_TYPE_ENT512("</ENT512Message>"),
    MESSAGE_TYPE_INVENTORY_STATUS("</InventoryStatus>"),

    MESSAGE_TYPE_DB_CEB312("c312"),
    MESSAGE_TYPE_DB_CEB622("c622"),
    MESSAGE_TYPE_DB_CEB624("c624"),
    MESSAGE_TYPE_DB_CEB626("c626"),
    MESSAGE_TYPE_DB_CEB816("c816"),
    MESSAGE_TYPE_DB_CEB818("c818"),
    MESSAGE_TYPE_DB_CEB512("c512"),
    MESSAGE_TYPE_DB_ENT512("e512"),
    MESSAGE_TYPE_DB_INVENTORY_STATUS("invtStatus"),
    ;

    private String value;

    private MessageTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
