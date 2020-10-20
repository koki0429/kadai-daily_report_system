package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "reactions")
@NamedQueries({
    @NamedQuery(
            name = "getReaction",
            query = "select re from Reaction as re where re.reactioned_id = :reactioned_id and re.report = :report"
            ),
    @NamedQuery(
            name = "getAllReactions",
            query = "select re from Reaction as re where re.reactioned_id = :reactioned_id and re.report = :report and re.reaction_flag = :reaction_flag"
            ),
    @NamedQuery(
            name = "getAllReactions_num",
            query = "select count(re) from Reaction as re where re.reactioned_id = :reactioned_id and re.report = :report"
            ),
    @NamedQuery(
            name = "getAllReaction",
            query = "select count(re) from Reaction as re where re.reactioned_id = :reactioned_id and re.report = :report"
            ),
    @NamedQuery(
            name = "getAllReaction_num",
            query = "select count(re) from Reaction as re where re.report = :report and re.reaction_flag = :reaction_flag"
            )
})

@Entity
public class Reaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    @ManyToOne
    @JoinColumn(name = "reactioned_id", nullable = false)
    private Employee reactioned_id;

    @Column(name = "reaction_flag")
    private Integer reaction_flag;

    public Employee getReactioned_id() {
        return reactioned_id;
    }

    public void setReactioned_id(Employee reactioned_id) {
        this.reactioned_id = reactioned_id;
    }

    public Integer getReaction_flag() {
        return reaction_flag;
    }

    public void setReaction_flag(Integer reaction_flag) {
        this.reaction_flag = reaction_flag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

}
