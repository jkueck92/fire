package de.jkueck.fire.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseListResponse<T> {

    private LinkedHashSet<T> data = new LinkedHashSet<>();

    public void add(T t) {
        this.data.add(t);
    }

}
