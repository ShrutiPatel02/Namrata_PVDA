package model.entity.admin;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Aug 05 19:31:31 IST 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DisciplinaryActionTypeEOImpl extends EntityImpl {
    private static EntityDefImpl mDefinitionObject;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Disciplinaryactiontypecode {
            public Object get(DisciplinaryActionTypeEOImpl obj) {
                return obj.getDisciplinaryactiontypecode();
            }

            public void put(DisciplinaryActionTypeEOImpl obj, Object value) {
                obj.setDisciplinaryactiontypecode((String)value);
            }
        }
        ,
        Disciplinaryactiontypedesc {
            public Object get(DisciplinaryActionTypeEOImpl obj) {
                return obj.getDisciplinaryactiontypedesc();
            }

            public void put(DisciplinaryActionTypeEOImpl obj, Object value) {
                obj.setDisciplinaryactiontypedesc((String)value);
            }
        }
        ,
        Disciplinaryactiontypename {
            public Object get(DisciplinaryActionTypeEOImpl obj) {
                return obj.getDisciplinaryactiontypename();
            }

            public void put(DisciplinaryActionTypeEOImpl obj, Object value) {
                obj.setDisciplinaryactiontypename((String)value);
            }
        }
        ,
        IsCorrecttive {
            public Object get(DisciplinaryActionTypeEOImpl obj) {
                return obj.getIsCorrecttive();
            }

            public void put(DisciplinaryActionTypeEOImpl obj, Object value) {
                obj.setIsCorrecttive((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(DisciplinaryActionTypeEOImpl object);

        public abstract void put(DisciplinaryActionTypeEOImpl object,
                                 Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }

    public static final int DISCIPLINARYACTIONTYPECODE = AttributesEnum.Disciplinaryactiontypecode.index();
    public static final int DISCIPLINARYACTIONTYPEDESC = AttributesEnum.Disciplinaryactiontypedesc.index();
    public static final int DISCIPLINARYACTIONTYPENAME = AttributesEnum.Disciplinaryactiontypename.index();
    public static final int ISCORRECTTIVE = AttributesEnum.IsCorrecttive.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DisciplinaryActionTypeEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = EntityDefImpl.findDefObject("model.entity.admin.DisciplinaryActionTypeEO");
        }
        return mDefinitionObject;
    }

    /**
     * Gets the attribute value for Disciplinaryactiontypecode, using the alias name Disciplinaryactiontypecode.
     * @return the Disciplinaryactiontypecode
     */
    public String getDisciplinaryactiontypecode() {
        return (String)getAttributeInternal(DISCIPLINARYACTIONTYPECODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for Disciplinaryactiontypecode.
     * @param value value to set the Disciplinaryactiontypecode
     */
    public void setDisciplinaryactiontypecode(String value) {
        setAttributeInternal(DISCIPLINARYACTIONTYPECODE, value);
    }

    /**
     * Gets the attribute value for Disciplinaryactiontypedesc, using the alias name Disciplinaryactiontypedesc.
     * @return the Disciplinaryactiontypedesc
     */
    public String getDisciplinaryactiontypedesc() {
        return (String)getAttributeInternal(DISCIPLINARYACTIONTYPEDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for Disciplinaryactiontypedesc.
     * @param value value to set the Disciplinaryactiontypedesc
     */
    public void setDisciplinaryactiontypedesc(String value) {
        setAttributeInternal(DISCIPLINARYACTIONTYPEDESC, value);
    }

    /**
     * Gets the attribute value for Disciplinaryactiontypename, using the alias name Disciplinaryactiontypename.
     * @return the Disciplinaryactiontypename
     */
    public String getDisciplinaryactiontypename() {
        return (String)getAttributeInternal(DISCIPLINARYACTIONTYPENAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for Disciplinaryactiontypename.
     * @param value value to set the Disciplinaryactiontypename
     */
    public void setDisciplinaryactiontypename(String value) {
        setAttributeInternal(DISCIPLINARYACTIONTYPENAME, value);
    }

    /**
     * Gets the attribute value for IsCorrecttive, using the alias name IsCorrecttive.
     * @return the IsCorrecttive
     */
    public String getIsCorrecttive() {
        return (String)getAttributeInternal(ISCORRECTTIVE);
    }

    /**
     * Sets <code>value</code> as the attribute value for IsCorrecttive.
     * @param value value to set the IsCorrecttive
     */
    public void setIsCorrecttive(String value) {
        setAttributeInternal(ISCORRECTTIVE, value);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index,
                                           AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value,
                                         AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }


    /**
     * @param disciplinaryactiontypecode key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String disciplinaryactiontypecode) {
        return new Key(new Object[]{disciplinaryactiontypecode});
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        super.create(attributeList);
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
        super.lock();
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        super.doDML(operation, e);
    }
}
