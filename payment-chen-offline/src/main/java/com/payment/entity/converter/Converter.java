package com.payment.entity.converter;

/**
 * 
 * @author qit on 2016/1/15
 *
 */
public interface Converter<S, T> {
	
    T ConvertTo(S obj);
    
}
