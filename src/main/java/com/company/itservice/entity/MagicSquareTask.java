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
@Table(name = "MAGIC_SQUARE_TASK")
@Entity
public class MagicSquareTask {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @Column(name = "DATE_", nullable = false)
    private LocalDateTime date;

    @Column(name = "INPUT_VALUE", nullable = false, length = 60)
    @NotNull
    private String inputValue;

    @JmixProperty
    @Transient
    private String outputValue;

    @JmixProperty
    @Transient
    private Integer result;

    @JmixProperty
    @Transient
    private FileRef dataFile;

    public FileRef getDataFile() {
        return dataFile;
    }

    public void setDataFile(FileRef dataFile) {
        this.dataFile = dataFile;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(String outputValue) {
        this.outputValue = outputValue;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}