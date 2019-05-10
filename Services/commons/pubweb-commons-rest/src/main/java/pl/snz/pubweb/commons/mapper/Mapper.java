package pl.snz.pubweb.commons.mapper;

public interface Mapper<T,R> {
    R transfrom (T input);
}
