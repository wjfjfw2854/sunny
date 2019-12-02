package cn.wjf.approomorm.network;


public class ProtocolDef {
    public String id;
    public String module = "";

    public ProtocolDef(String module, String id) {
        this.id = id;
        if (module != null) {
            this.module = module;
        }
    }

    public ProtocolDef(String module, int id) {
        this(module, String.valueOf(id));
    }
}
