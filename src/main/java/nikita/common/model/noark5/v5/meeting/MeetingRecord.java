package nikita.common.model.noark5.v5.meeting;

import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v5.Record;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.InheritanceType.JOINED;
import static nikita.common.config.Constants.TABLE_MEETING_RECORD;
import static nikita.common.config.N5ResourceMappings.MEETING_RECORD;

@Entity
@Table(name = TABLE_MEETING_RECORD)
@Inheritance(strategy = JOINED)
public class MeetingRecord
        extends Record {

    /**
     * M085 - moeteregistreringstype (xs:string)
     */
    @Column(name = "meeting_record_type")
    @Audited
    private String meetingRecordType;

    /**
     * M088 - moetesakstype (xs:string)
     */
    @Column(name = "meeting_case_type")
    @Audited
    private String meetingCaseType;

    /**
     * M305 - administrativEnhet (xs:string)
     */
    @Column(name = "meeting_record_status")
    @Audited
    private String meetingRecordStatus;

    /**
     * M305 - administrativEnhet (xs:string)
     */
    @Column(name = "administrative_unit")
    @Audited
    private String administrativeUnit;

    /**
     * M307 - saksbehandler
     */
    @Column(name = "case_handler")
    @Audited
    private String caseHandler;

    /**
     * M223 - referanseTilMoeteregistrering (xs:string)
     **/
    // Link to "to"  MeetingRegistration
    @OneToOne(fetch = LAZY)
    private MeetingRecord referenceToMeetingRegistration;

    /**
     * M224 - referanseFraMoeteregistrering (xs:string)
     **/
    // Link to "from" MeetingRegistration
    @OneToOne(fetch = LAZY)
    private MeetingRecord referenceFromMeetingRegistration;

    public String getMeetingRecordType() {
        return meetingRecordType;
    }

    public void setMeetingRecordType(String meetingRecordType) {
        this.meetingRecordType = meetingRecordType;
    }

    public String getMeetingCaseType() {
        return meetingCaseType;
    }

    public void setMeetingCaseType(String meetingCaseType) {
        this.meetingCaseType = meetingCaseType;
    }

    public String getMeetingRecordStatus() {
        return meetingRecordStatus;
    }

    public void setMeetingRecordStatus(String meetingRecordStatus) {
        this.meetingRecordStatus = meetingRecordStatus;
    }

    public String getAdministrativeUnit() {
        return administrativeUnit;
    }

    public void setAdministrativeUnit(String administrativeUnit) {
        this.administrativeUnit = administrativeUnit;
    }

    public String getCaseHandler() {
        return caseHandler;
    }

    public void setCaseHandler(String caseHandler) {
        this.caseHandler = caseHandler;
    }


    @Override
    public String getBaseTypeName() {
        return MEETING_RECORD;
    }

    public MeetingRecord getReferenceToMeetingRegistration() {
        return referenceToMeetingRegistration;
    }

    public void setReferenceToMeetingRegistration(MeetingRecord referenceToMeetingRegistration) {
        this.referenceToMeetingRegistration = referenceToMeetingRegistration;
    }

    public MeetingRecord getReferenceFromMeetingRegistration() {
        return referenceFromMeetingRegistration;
    }

    public void setReferenceFromMeetingRegistration(MeetingRecord referenceFromMeetingRegistration) {
        this.referenceFromMeetingRegistration = referenceFromMeetingRegistration;
    }

    @Override
    public String toString() {
        return "MeetingRecord{" + super.toString() +
                ", caseHandler='" + caseHandler + '\'' +
                ", administrativeUnit='" + administrativeUnit + '\'' +
                ", meetingRecordStatus='" + meetingRecordStatus + '\'' +
                ", meetingCaseType='" + meetingCaseType + '\'' +
                ", meetingRecordType='" + meetingRecordType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != getClass()) {
            return false;
        }
        MeetingRecord rhs = (MeetingRecord) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(caseHandler, rhs.caseHandler)
                .append(administrativeUnit, rhs.administrativeUnit)
                .append(meetingRecordStatus, rhs.meetingRecordStatus)
                .append(meetingCaseType, rhs.meetingCaseType)
                .append(meetingRecordType, rhs.meetingRecordType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(caseHandler)
                .append(administrativeUnit)
                .append(meetingRecordStatus)
                .append(meetingCaseType)
                .append(meetingRecordType)
                .toHashCode();
    }
}
