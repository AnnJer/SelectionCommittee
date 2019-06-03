package com.kpi.javaee.servlet.entities;

import javax.persistence.*;

@Entity
@Table(name = "subjects", schema = "public", catalog = "selectioncommittee")
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String label;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "label", nullable = false, length = 15)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubjectEntity that = (SubjectEntity) o;

        if (id != that.id) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;

        return true;
    }

}
