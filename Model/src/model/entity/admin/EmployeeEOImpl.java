package model.entity.admin;

import oracle.jbo.Key;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Aug 08 12:37:42 IST 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class EmployeeEOImpl extends EntityImpl {
    private static EntityDefImpl mDefinitionObject;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Employeeid {
            public Object get(EmployeeEOImpl obj) {
                return obj.getEmployeeid();
            }

            public void put(EmployeeEOImpl obj, Object value) {
                obj.setEmployeeid((Number)value);
            }
        }
        ,
        Firstname {
            public Object get(EmployeeEOImpl obj) {
                return obj.getFirstname();
            }

            public void put(EmployeeEOImpl obj, Object value) {
                obj.setFirstname((String)value);
            }
        }
        ,
        Lastname {
            public Object get(EmployeeEOImpl obj) {
                return obj.getLastname();
            }

            public void put(EmployeeEOImpl obj, Object value) {
                obj.setLastname((String)value);
            }
        }
        ,
        Departmentid {
            public Object get(EmployeeEOImpl obj) {
                return obj.getDepartmentid();
            }

            public void put(EmployeeEOImpl obj, Object value) {
                obj.setDepartmentid((Number)value);
            }
        }
        ,
        Managerid {
            public Object get(EmployeeEOImpl obj) {
                return obj.getManagerid();
            }

            public void put(EmployeeEOImpl obj, Object value) {
                obj.setManagerid((Number)value);
            }
        }
        ,
        EmployeeEO {
            public Object get(EmployeeEOImpl obj) {
                return obj.getEmployeeEO();
            }

            public void put(EmployeeEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ManageridEmployeeEO {
            public Object get(EmployeeEOImpl obj) {
                return obj.getManageridEmployeeEO();
            }

            public void put(EmployeeEOImpl obj, Object value) {
                obj.setManageridEmployeeEO((EmployeeEOImpl)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(EmployeeEOImpl object);

        public abstract void put(EmployeeEOImpl object, Object value);

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


    public static final int EMPLOYEEID = AttributesEnum.Employeeid.index();
    public static final int FIRSTNAME = AttributesEnum.Firstname.index();
    public static final int LASTNAME = AttributesEnum.Lastname.index();
    public static final int DEPARTMENTID = AttributesEnum.Departmentid.index();
    public static final int MANAGERID = AttributesEnum.Managerid.index();
    public static final int EMPLOYEEEO = AttributesEnum.EmployeeEO.index();
    public static final int MANAGERIDEMPLOYEEEO = AttributesEnum.ManageridEmployeeEO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public EmployeeEOImpl() {
    }


    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        if (mDefinitionObject == null) {
            mDefinitionObject = EntityDefImpl.findDefObject("model.entity.admin.EmployeeEO");
        }
        return mDefinitionObject;
    }

    /**
     * Gets the attribute value for Employeeid, using the alias name Employeeid.
     * @return the Employeeid
     */
    public Number getEmployeeid() {
        return (Number)getAttributeInternal(EMPLOYEEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for Employeeid.
     * @param value value to set the Employeeid
     */
    public void setEmployeeid(Number value) {
        setAttributeInternal(EMPLOYEEID, value);
    }

    /**
     * Gets the attribute value for Firstname, using the alias name Firstname.
     * @return the Firstname
     */
    public String getFirstname() {
        return (String)getAttributeInternal(FIRSTNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for Firstname.
     * @param value value to set the Firstname
     */
    public void setFirstname(String value) {
        setAttributeInternal(FIRSTNAME, value);
    }

    /**
     * Gets the attribute value for Lastname, using the alias name Lastname.
     * @return the Lastname
     */
    public String getLastname() {
        return (String)getAttributeInternal(LASTNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for Lastname.
     * @param value value to set the Lastname
     */
    public void setLastname(String value) {
        setAttributeInternal(LASTNAME, value);
    }

    /**
     * Gets the attribute value for Departmentid, using the alias name Departmentid.
     * @return the Departmentid
     */
    public Number getDepartmentid() {
        return (Number)getAttributeInternal(DEPARTMENTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for Departmentid.
     * @param value value to set the Departmentid
     */
    public void setDepartmentid(Number value) {
        setAttributeInternal(DEPARTMENTID, value);
    }

    /**
     * Gets the attribute value for Managerid, using the alias name Managerid.
     * @return the Managerid
     */
    public Number getManagerid() {
        return (Number)getAttributeInternal(MANAGERID);
    }

    /**
     * Sets <code>value</code> as the attribute value for Managerid.
     * @param value value to set the Managerid
     */
    public void setManagerid(Number value) {
        setAttributeInternal(MANAGERID, value);
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
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getEmployeeEO() {
        return (RowIterator)getAttributeInternal(EMPLOYEEEO);
    }

    /**
     * @return the associated entity EmployeeEOImpl.
     */
    public EmployeeEOImpl getManageridEmployeeEO() {
        return (EmployeeEOImpl)getAttributeInternal(MANAGERIDEMPLOYEEEO);
    }

    /**
     * Sets <code>value</code> as the associated entity EmployeeEOImpl.
     */
    public void setManageridEmployeeEO(EmployeeEOImpl value) {
        setAttributeInternal(MANAGERIDEMPLOYEEEO, value);
    }

    /**
     * @param employeeid key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(Number employeeid) {
        return new Key(new Object[]{employeeid});
    }


}
