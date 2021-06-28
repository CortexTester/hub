package com.cbx.hub.routing.model

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
    var name: String,
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "party_dialect",
        joinColumns = [JoinColumn(name = "party_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "dialect_id", referencedColumnName = "id")]
    )
    var dialects: MutableList<Dialect> = mutableListOf(),
    var url: String,
    var clientSidePartyId: Long?,
    var apiKey: String

)

@Entity
data class Dialect(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
    var name: String,
    @ManyToMany(mappedBy = "dialects", fetch = FetchType.LAZY)
    var parties: MutableList<Party> = mutableListOf()
)
