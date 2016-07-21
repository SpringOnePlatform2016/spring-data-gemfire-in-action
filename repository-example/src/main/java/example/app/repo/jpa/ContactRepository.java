package example.app.repo.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import example.app.model.Contact;

/**
 * Spring Data {@link JpaRepository} interface for performing basic data access, CRUD and query operations on
 * {@link Contact} objects stored and managed in a relational database (e.g. an RDBMS such as MySQL).
 *
 * @author John Blum
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see example.app.model.Contact
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
