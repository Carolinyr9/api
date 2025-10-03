package com.eventostec.api.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.eventostec.api.domain.cupon.Coupon;
import com.eventostec.api.domain.cupon.CouponRequestDTO;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.repositories.CouponRepository;
import com.eventostec.api.repositories.EventRepository;

@Service
public class CouponService {
    
    private final CouponRepository couponRepository;
    private final EventRepository eventRepository;

    public CouponService(CouponRepository couponRepository, EventRepository eventRepository) {
        this.couponRepository = couponRepository;
        this.eventRepository = eventRepository;
    }

    public Coupon addCouponToEvent(UUID eventId, CouponRequestDTO couponData) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Coupon coupon = new Coupon();
        coupon.setCode(couponData.code());
        coupon.setDiscount(couponData.discount());
        coupon.setValid(new java.util.Date(couponData.valid()));
        coupon.setEvent(event);

        return couponRepository.save(coupon);
    }

    public List<Coupon> consultCoupons(UUID eventId, Date currentDate){
        return couponRepository.findByEventIdAndValidAfter(eventId, currentDate);
    }
    
}
