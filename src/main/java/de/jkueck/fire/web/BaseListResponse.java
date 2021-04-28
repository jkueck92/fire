package de.jkueck.fire.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseListResponse<T> {

    private Set<T> data = new HashSet<>();

    public void add(T t) {
       this.data.add(t);
    }

}
