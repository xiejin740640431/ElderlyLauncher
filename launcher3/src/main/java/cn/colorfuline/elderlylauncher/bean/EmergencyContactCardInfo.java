package cn.colorfuline.elderlylauncher.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;


/**
 * 紧急联系卡实体
 * Created by CQDXP on 2016/5/28.
 */
public class EmergencyContactCardInfo implements Serializable {
    public static final String KEY = "EmergencyContactCardInfo";
    public static final String ISOPEN = "EmergencyContactCardInfoIsOpen";

    private String keyWord = "1";

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * id : 5
     * userId : 2
     * photoUrl : http://www.baidu.com
     * name : null
     * birthday : null
     * medicalStatus : null
     * anaphylaxis : null
     * medicationUse : null
     * medicalRecord : null
     * emergencyContact : null
     * bloodType : null
     * weight : null
     * height : null
     * personalStatement : null
     * createTime : null
     * status : null
     * myPhoneNumber : null
     */

    private UserHealthInfo userHealthInfo;
    /**
     * id : 8
     * userId : 2
     * contactName : 张三
     * contactType : 1
     * contactNo : 18627544250
     * isDefault : null
     * status : null
     */

    private List<ContactsBean> userEmergencyContacts;

    public UserHealthInfo getUserHealthInfo() {
        return userHealthInfo;
    }

    public void setUserHealthInfo(UserHealthInfo userHealthInfo) {
        this.userHealthInfo = userHealthInfo;
    }

    public List<ContactsBean> getContacts() {
        return userEmergencyContacts;
    }

    public void setContacts(List<ContactsBean> userEmergencyContacts) {
        this.userEmergencyContacts = userEmergencyContacts;
    }

    public static class UserHealthInfo implements Serializable {
        private Integer id;
        private String userId;
        private String photoUrl;
        private String name;
        private String birthday;
        private String medicalStatus;
        private String anaphylaxis;
        private String medicationUse;
        private String medicalRecord;
        private String emergencyContact;
        private String bloodType;
        private String weight;
        private String height;
        private String personalStatement;
        private String createTime;
        private String status;
        private String myPhoneNumber;
        private boolean isOpen;

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean open) {
            isOpen = open;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getMedicalStatus() {
            return medicalStatus;
        }

        public void setMedicalStatus(String medicalStatus) {
            this.medicalStatus = medicalStatus;
        }

        public String getAnaphylaxis() {
            return anaphylaxis;
        }

        public void setAnaphylaxis(String anaphylaxis) {
            this.anaphylaxis = anaphylaxis;
        }

        public String getMedicationUse() {
            return medicationUse;
        }

        public void setMedicationUse(String medicationUse) {
            this.medicationUse = medicationUse;
        }

        public String getMedicalRecord() {
            return medicalRecord;
        }

        public void setMedicalRecord(String medicalRecord) {
            this.medicalRecord = medicalRecord;
        }

        public String getEmergencyContact() {
            return emergencyContact;
        }

        public void setEmergencyContact(String emergencyContact) {
            this.emergencyContact = emergencyContact;
        }

        public String getBloodType() {
            return bloodType;
        }

        public void setBloodType(String bloodType) {
            this.bloodType = bloodType;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getPersonalStatement() {
            return personalStatement;
        }

        public void setPersonalStatement(String personalStatement) {
            this.personalStatement = personalStatement;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMyPhoneNumber() {
            return myPhoneNumber;
        }

        public void setMyPhoneNumber(String myPhoneNumber) {
            this.myPhoneNumber = myPhoneNumber;
        }


        public UserHealthInfo() {
        }

        public String getBloodTypeInt(String bloodType) {
            if ("O型".equals(bloodType)) {
                return "0";
            } else if ("A型".equals(bloodType)) {
                return "1";
            } else if ("B型".equals(bloodType)) {
                return "2";
            } else if ("AB型".equals(bloodType)) {
                return "3";
            }
            return "";
        }

        public String getBloodTypeString(String bloodType) {
            if ("0".equals(bloodType)) {
                return "O型";
            } else if ("1".equals(bloodType)) {
                return "A型";
            } else if ("2".equals(bloodType)) {
                return "B型";
            } else if ("3".equals(bloodType)) {
                return "AB型";
            }
            return "";
        }
    }

    public static class ContactsBean implements Serializable {
        private Integer id;
        private String userId;
        private String contactName;
        private String contactType;
        private String contactNo;
        private String isDefault;
        private String status;

        public ContactsBean() {
        }

        public ContactsBean(String contactName, String contactNo, String contactType, String userId) {
            this.contactName = contactName;
            this.contactNo = contactNo;
            this.contactType = contactType;
            this.userId = userId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactType() {
            return contactType;
        }

        public void setContactType(String contactType) {
            this.contactType = contactType;
        }

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
        }

        public String getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(String isDefault) {
            this.isDefault = isDefault;
        }

        public boolean getIsDefaultBoolean() {
            return "1".equals(getIsDefault()) ? true : false;
        }

        public void setIsDefault(boolean isDefault) {
            if (isDefault) {
                setIsDefault("1");
            } else {
                setIsDefault("0");
            }
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (o instanceof ContactsBean) {
                ContactsBean contactsBean = (ContactsBean) o;
                if (!TextUtils.isEmpty(getContactNo()) && getContactNo().equals(contactsBean.getContactNo())) {
                    return true;
                } else {
                    return false;
                }
            }
            return super.equals(o);
        }
    }
}
