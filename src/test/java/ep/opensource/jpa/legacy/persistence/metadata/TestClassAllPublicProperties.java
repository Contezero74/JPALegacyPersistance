package ep.opensource.jpa.legacy.persistence.metadata;

import java.util.Calendar;
import java.util.Date;

import ep.opensource.jpa.legacy.persistence.annotation.Basic;
import ep.opensource.jpa.legacy.persistence.annotation.Column;
import ep.opensource.jpa.legacy.persistence.annotation.Entity;
import ep.opensource.jpa.legacy.persistence.annotation.Enumerated;
import ep.opensource.jpa.legacy.persistence.annotation.GeneratedValue;
import ep.opensource.jpa.legacy.persistence.annotation.Id;
import ep.opensource.jpa.legacy.persistence.annotation.PostLoad;
import ep.opensource.jpa.legacy.persistence.annotation.Table;
import ep.opensource.jpa.legacy.persistence.annotation.Temporal;
import ep.opensource.jpa.legacy.persistence.annotation.Transient;
import ep.opensource.jpa.legacy.persistence.metadata.ColumnMetadataTest.TestEnum;

/**
 * This class is used during the test to verify the information that we can retrieve using the
 * {@link ColumnMetadata} class.
 */
@Entity(name=TestClassAllPublicProperties.ENTITY_LABEL)
@Table(name=TestClassAllPublicProperties.TABLE_LABEL, schema=TestClassAllPublicProperties.SCHEMA_NAME)
class TestClassAllPublicProperties {
    @Transient public String transientProperty;
    
    @Id public int id;
    @Id @GeneratedValue() public Integer secondaryId;
    
    @Basic(optional=true) public long isOptional;
    
    @Basic(optional=false) public long isNotOptional;
    
    @Column(nullable=true) public long isNullable;
    
    @Column(nullable=false) public long isNotNullable;
    
    @Basic(optional=true) @Column(nullable=true) public long isOptionalAndNullable;
    
    @Basic(optional=true) @Column(nullable=false) public long isOptionalButNotNullable;
    
    @Basic(optional=false) @Column(nullable=true) public long isNotOptionalButNullable;
    
    @Basic(optional=false) @Column(nullable=false) public long isNotOptionalAndNotNullable;
    
    @Column(insertable = true, updatable=true) public String insertableAndUpdatable;
    
    @Column(insertable = true, updatable=false) public String insertableButNotUpdatable;
    
    @Column(insertable = false, updatable=true) public String notInsertableButUpdatable;
    
    @Column(insertable = false, updatable=false) public String notInsertableAndNotUpdatable;
    
    @Column(name=ColumnMetadataTest.COLUMN_ENUM1_LABEL) public TestEnum enum1;
    
    @Enumerated @Column(table=ColumnMetadataTest.SECONDARY_TABLE_LABEL) public TestEnum enum2;
    
    @Enumerated(EnumType.STRING) @Column(name=ColumnMetadataTest.COLUMN_ENUM3_LABEL) public TestEnum enum3;
    
    @Temporal(TemporalType.DATE) public Date date;
    
    @Temporal(TemporalType.TIME) public Date time;
    
    @Temporal(TemporalType.TIMESTAMP) public Calendar calendar;
    
    public Calendar noTemporalCalendar;
    
    @PostLoad
    private void init() {
    	transientProperty = "EndValue";
    }
    
    public static final String ENTITY_LABEL = "TestEntityLabel";
    public static final String TABLE_LABEL = "TestTableLabel";
    public static final String SCHEMA_NAME = "TestSchemaName";
}

