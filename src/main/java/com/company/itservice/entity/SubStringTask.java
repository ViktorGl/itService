package com.company.itservice.entity;

import io.jmix.core.FileRef;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "SUB_STRING_TASK")
@Entity
public class SubStringTask {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "DATE_", nullable = false)
    @NotNull
    private LocalDateTime date;

    @Column(name = "SUB_STRINGS", nullable = false)
    @Lob
    @NotNull
    private String subStrings;

    @Column(name = "CMP_STRINGS", nullable = false)
    @NotNull
    private String cmpStrings;

    @JmixProperty
    @Transient
    private String resultString;

    @JmixProperty
    @Transient
    private FileRef dataFile;

    public FileRef getDataFile() {
        return dataFile;
    }

    public void setDataFile(FileRef dataFile) {
        this.dataFile = dataFile;
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public String getCmpStrings() {
        return cmpStrings;
    }

    public void setCmpStrings(String cmpStrings) {
        this.cmpStrings = cmpStrings;
    }

    public String getSubStrings() {
        return subStrings;
    }

    public void setSubStrings(String subStrings) {
        this.subStrings = subStrings;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}