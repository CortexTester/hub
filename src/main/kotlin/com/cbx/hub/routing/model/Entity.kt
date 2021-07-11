package com.cbx.hub.routing.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(
    name = "Party",
    uniqueConstraints = [UniqueConstraint(columnNames = ["apiKey", "clientSidePartyId"])]
)
data class Party(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String,
    @ManyToMany(cascade = [CascadeType.MERGE], fetch = FetchType.LAZY)
    @JoinTable(
        name = "party_dialect",
        joinColumns = [JoinColumn(name = "party_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "dialect_id", referencedColumnName = "id")]
    )
    @JsonIgnore
    var dialects: MutableList<Dialect> = mutableListOf(),
    var url: String,
    var clientSidePartyId: Long?,
    var apiKey: String
){
    override fun toString(): String {
        return "Party(id=$id, name='$name', url=$url, clientSidePartyId=$clientSidePartyId)"
    }
}

@Entity
data class Dialect(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String,
    @ManyToMany(mappedBy = "dialects", cascade = [CascadeType.MERGE], fetch = FetchType.LAZY)
    var parties: MutableList<Party> = mutableListOf()
)
{
    override fun toString(): String {
        return "Party(id=$id, name='$name')"
    }
}
