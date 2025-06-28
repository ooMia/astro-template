package com.example.mistakes.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.mistakes.base.type.FsMeta;
import com.example.mistakes.base.type.Identifiable;

/**
 * ReadOnlyFsRepository
 * <p>
 * Repository interface for read-only operations
 *
 * @param <T> Entity
 * @see org.springframework.data.repository.Repository
 */
@Repository
class ReadOnlyFsRepository<T extends Identifiable<ID> & FsMeta, ID>
    implements CrudRepository<T, ID> {

  Map<ID, T> data = new HashMap<>();

  @Override
  public @NonNull <S extends T> S save(@NonNull S entity) {
    data.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public @NonNull <S extends T> Iterable<S> saveAll(
      @NonNull Iterable<S> entities) {
    // use parallelStream() if entities came as a Collection and have many
    // elements
    entities.forEach(this::save);
    return entities;
  }

  @Override
  public @NonNull Optional<T> findById(@NonNull ID id) {
    return Optional.ofNullable(data.get(id));
  }

  @Override
  public @NonNull Iterable<T> findAll() {
    return List.copyOf(data.values());
  }

  @Override
  public @NonNull Iterable<T> findAllById(@NonNull Iterable<ID> ids) {
    return StreamSupport.stream(ids.spliterator(), true).map(id -> data.get(id))
        .filter(Objects::nonNull).collect(Collectors.toList());
  }

  public @NonNull Iterable<T> findAllByPattern(@NonNull String regex) {
    Pattern pattern = Pattern.compile(regex);
    return data.values().parallelStream()
        .filter(entity -> pattern.matcher(entity.getId().toString()).matches())
        .collect(Collectors.toList());
  }

  @Override
  public boolean existsById(ID id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
        "Unimplemented method 'existsById'");
  }

  @Override
  public long count() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'count'");
  }

  @Override
  public void deleteById(ID id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
        "Unimplemented method 'deleteById'");
  }

  @Override
  public void delete(T entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public void deleteAllById(Iterable<? extends ID> ids) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
        "Unimplemented method 'deleteAllById'");
  }

  @Override
  public void deleteAll(Iterable<? extends T> entities) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
  }

  @Override
  public void deleteAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
  }
}
