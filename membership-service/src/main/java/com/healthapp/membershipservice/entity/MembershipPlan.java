package com.healthapp.membershipservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "membership_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_name", nullable = false, length = 50)
    private String planName; // FREE, PREMIUM, FAMILY

    @Column(name = "max_emergency_contacts")
    private Integer maxEmergencyContacts;

    @Column(name = "facility_search_radius_km")
    private Integer facilitySearchRadiusKm;

    @Column(name = "price_monthly", precision = 10, scale = 2)
    private BigDecimal priceMonthly;
}
