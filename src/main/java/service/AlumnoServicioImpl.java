package service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import model.Alumno;

@Service
public class AlumnoServicioImpl implements AlumnoServicio {

	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	@Override
	public void alta(Alumno alumno) {
		if(buscarPorNombre(alumno) == null) {
			entityManager.persist(alumno);
		}
	}

	@Override
	public List<Alumno> buscarPorCurso(String curso) {
		String jpql = "select a from Alumno a where a.curso=:curso";
		TypedQuery<Alumno> query = entityManager.createQuery(jpql, Alumno.class);
		query.setParameter("curso", curso);
		return query.getResultList();
	}

	@Override
	public List<String> cursos() {
		String jpql = "select distinct(a.curso) from Alumno a";
		TypedQuery<String> query = entityManager.createQuery(jpql, String.class);
		return query.getResultList();
	}
	
	public Alumno buscarPorNombre(Alumno a) {
		String jpql = "select a from Alumno a where a.nombre=:nombre";
		TypedQuery<Alumno> query = entityManager.createQuery(jpql, Alumno.class);
		query.setParameter("nombre", a.getNombre());
		List<Alumno> alumnos = query.getResultList();
		return alumnos.isEmpty()?null:alumnos.get(0);
	}
}
