package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.CompteComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.EcritureComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.JournalComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.LigneEcritureComptableRM;
import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.SequenceEcritureRM;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Types;
import java.util.List;


/**
 * A
 * Implémentation de l'interface {@link ComptabiliteDao}
 */
public class ComptabiliteDaoImpl extends AbstractDbConsumer implements ComptabiliteDao {

	private static final ComptabiliteDaoImpl INSTANCE = new ComptabiliteDaoImpl();
	private static String sqlGetListCompteComptable;
	private static String sqlGetListJournalComptable;
	private static String sqlGetListEcritureComptable;
	private static String sqlGetEcritureComptable;
	private static String sqlGetEcritureComptableByRef;
	private static String sqlLoadListLigneEcriture;
	private static String sqlInsertEcritureComptable;
	private static String sqlInsertListLigneEcritureComptable;
	private static String sqlUpdateEcritureComptable;
	private static String sqlDeleteEcritureComptable;
	private static String sqlDeleteListLigneEcritureComptable;
	private static String sqlSelectSequenceEcritureComptable;
	private static String sqlUpdateSequenceEcritureComptable;
	private static String sqlInsertSequenceEcritureComptable;

	protected ComptabiliteDaoImpl() {
		super();
	}

	public static ComptabiliteDaoImpl getInstance() {
		return ComptabiliteDaoImpl.INSTANCE;
	}

	public static void setSqlUpdateSequenceEcritureComptable(String sqlUpdateSequenceEcritureComptable) {
		ComptabiliteDaoImpl.sqlUpdateSequenceEcritureComptable = sqlUpdateSequenceEcritureComptable;
	}

	public static void setSqlInsertSequenceEcritureComptable(String sqlInsertSequenceEcritureComptable) {
		ComptabiliteDaoImpl.sqlInsertSequenceEcritureComptable = sqlInsertSequenceEcritureComptable;
	}

	public static void setSqlSelectSequenceEcritureComptable(String sqlSelectSequenceEcritureComptable) {
		ComptabiliteDaoImpl.sqlSelectSequenceEcritureComptable = sqlSelectSequenceEcritureComptable;
	}

	public static void setSqlGetListCompteComptable(String pSQLgetListCompteComptable) {
		ComptabiliteDaoImpl.sqlGetListCompteComptable = pSQLgetListCompteComptable;
	}

	public static void setSqlGetListJournalComptable(String pSQLgetListJournalComptable) {
		ComptabiliteDaoImpl.sqlGetListJournalComptable = pSQLgetListJournalComptable;
	}

	public static void setSqlGetListEcritureComptable(String pSQLgetListEcritureComptable) {
		ComptabiliteDaoImpl.sqlGetListEcritureComptable = pSQLgetListEcritureComptable;
	}

	public static void setSqlGetEcritureComptable(String pSQLgetEcritureComptable) {
		ComptabiliteDaoImpl.sqlGetEcritureComptable = pSQLgetEcritureComptable;
	}

	public static void setSqlGetEcritureComptableByRef(String pSQLgetEcritureComptableByRef) {
		ComptabiliteDaoImpl.sqlGetEcritureComptableByRef = pSQLgetEcritureComptableByRef;
	}

	public static void setSqlLoadListLigneEcriture(String pSQLloadListLigneEcriture) {
		ComptabiliteDaoImpl.sqlLoadListLigneEcriture = pSQLloadListLigneEcriture;
	}

	public static void setSqlInsertEcritureComptable(String pSQLinsertEcritureComptable) {
		ComptabiliteDaoImpl.sqlInsertEcritureComptable = pSQLinsertEcritureComptable;
	}

	public static void setSqlInsertListLigneEcritureComptable(String pSQLinsertListLigneEcritureComptable) {
		ComptabiliteDaoImpl.sqlInsertListLigneEcritureComptable = pSQLinsertListLigneEcritureComptable;
	}

	public static void setSqlUpdateEcritureComptable(String pSQLupdateEcritureComptable) {
		ComptabiliteDaoImpl.sqlUpdateEcritureComptable = pSQLupdateEcritureComptable;
	}

	public static void setSqlDeleteEcritureComptable(String pSQLdeleteEcritureComptable) {
		ComptabiliteDaoImpl.sqlDeleteEcritureComptable = pSQLdeleteEcritureComptable;
	}

	public static void setSqlDeleteListLigneEcritureComptable(String sqlDeleteListLigneEcritureComptable) {
		ComptabiliteDaoImpl.sqlDeleteListLigneEcritureComptable = sqlDeleteListLigneEcritureComptable;
	}

	@Override
	public Integer getDerniereValeurSequenceEcriture(String journalCode, Integer annee) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		sqlParameterSource.addValue("journal_code", journalCode);
		sqlParameterSource.addValue("annee", annee);
		SequenceEcritureRM rowMapper = new SequenceEcritureRM();
		List<SequenceEcritureComptable> sequenceEcritureComptableList = jdbcTemplate.query(sqlSelectSequenceEcritureComptable, sqlParameterSource, rowMapper);
		if (sequenceEcritureComptableList.isEmpty()) {
			return null;
		} else {
			return sequenceEcritureComptableList.get(0).getDerniereValeur();
		}
	}

	@Override
	public void insertSequenceEcriture(SequenceEcritureComptable sequenceEcritureComptable) {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("journal_code", sequenceEcritureComptable.getJournalCode());
		vSqlParams.addValue("annee", sequenceEcritureComptable.getAnnee());
		vSqlParams.addValue("derniere_valeur", sequenceEcritureComptable.getDerniereValeur());
		vJdbcTemplate.update(sqlInsertSequenceEcritureComptable, vSqlParams);
	}

	@Override
	public void updateSequenceEcriture(SequenceEcritureComptable sequenceEcritureComptable) {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("journal_code", sequenceEcritureComptable.getJournalCode());
		vSqlParams.addValue("annee", sequenceEcritureComptable.getAnnee());
		vSqlParams.addValue("derniere_valeur", sequenceEcritureComptable.getDerniereValeur());
		vJdbcTemplate.update(sqlUpdateSequenceEcritureComptable, vSqlParams);
	}

	@Override
	public List<CompteComptable> getListCompteComptable() {
		JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
		CompteComptableRM vRM = new CompteComptableRM();
		return vJdbcTemplate.query(sqlGetListCompteComptable, vRM);
	}

	@Override
	public List<JournalComptable> getListJournalComptable() {
		JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
		JournalComptableRM vRM = new JournalComptableRM();
		return vJdbcTemplate.query(sqlGetListJournalComptable, vRM);
	}

	@Override
	public List<EcritureComptable> getListEcritureComptable() {
		JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
		EcritureComptableRM vRM = new EcritureComptableRM();
		return vJdbcTemplate.query(sqlGetListEcritureComptable, vRM);
	}

	@Override
	public EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("id", pId);
		EcritureComptableRM vRM = new EcritureComptableRM();
		EcritureComptable vBean;
		try {
			vBean = vJdbcTemplate.queryForObject(sqlGetEcritureComptable, vSqlParams, vRM);
		} catch (EmptyResultDataAccessException vEx) {
			throw new NotFoundException("EcritureComptable non trouvée : id=" + pId);
		}
		return vBean;
	}

	@Override
	public EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("reference", pReference);
		EcritureComptableRM vRM = new EcritureComptableRM();
		EcritureComptable vBean;
		try {
			vBean = vJdbcTemplate.queryForObject(sqlGetEcritureComptableByRef, vSqlParams, vRM);
		} catch (EmptyResultDataAccessException vEx) {
			throw new NotFoundException("EcritureComptable non trouvée : reference=" + pReference);
		}
		return vBean;
	}

	@Override
	public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("ecriture_id", pEcritureComptable.getId());
		LigneEcritureComptableRM vRM = new LigneEcritureComptableRM();
		List<LigneEcritureComptable> vList = vJdbcTemplate.query(sqlLoadListLigneEcriture, vSqlParams, vRM);
		pEcritureComptable.getListLigneEcriture().clear();
		pEcritureComptable.getListLigneEcriture().addAll(vList);
	}

	@Override
	public void insertEcritureComptable(EcritureComptable pEcritureComptable) {
		// ===== Ecriture Comptable
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("journal_code", pEcritureComptable.getJournal().getCode());
		vSqlParams.addValue("reference", pEcritureComptable.getReference());
		vSqlParams.addValue("date", pEcritureComptable.getDate(), Types.DATE);
		vSqlParams.addValue("libelle", pEcritureComptable.getLibelle());

		vJdbcTemplate.update(sqlInsertEcritureComptable, vSqlParams);

		// ----- Récupération de l'id
		Integer vId = this.queryGetSequenceValuePostgreSQL(DataSourcesEnum.MYERP,
				"myerp.ecriture_comptable_id_seq",
				Integer.class);
		pEcritureComptable.setId(vId);

		// ===== Liste des lignes d'écriture
		this.insertListLigneEcritureComptable(pEcritureComptable);
	}

	/**
	 * Insert les lignes d'écriture de l'écriture comptable
	 *
	 * @param pEcritureComptable l'écriture comptable
	 */
	protected void insertListLigneEcritureComptable(EcritureComptable pEcritureComptable) {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("ecriture_id", pEcritureComptable.getId());

		int vLigneId = 0;
		for (LigneEcritureComptable vLigne : pEcritureComptable.getListLigneEcriture()) {
			vLigneId++;
			vSqlParams.addValue("ligne_id", vLigneId);
			vSqlParams.addValue("compte_comptable_numero", vLigne.getCompteComptable().getNumero());
			vSqlParams.addValue("libelle", vLigne.getLibelle());
			vSqlParams.addValue("debit", vLigne.getDebit());
			vSqlParams.addValue("credit", vLigne.getCredit());

			vJdbcTemplate.update(sqlInsertListLigneEcritureComptable, vSqlParams);
		}
	}

	@Override
	public void updateEcritureComptable(EcritureComptable pEcritureComptable) {
		// ===== Ecriture Comptable
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("id", pEcritureComptable.getId());
		vSqlParams.addValue("journal_code", pEcritureComptable.getJournal().getCode());
		vSqlParams.addValue("reference", pEcritureComptable.getReference());
		vSqlParams.addValue("date", pEcritureComptable.getDate(), Types.DATE);
		vSqlParams.addValue("libelle", pEcritureComptable.getLibelle());

		vJdbcTemplate.update(sqlUpdateEcritureComptable, vSqlParams);

		// ===== Liste des lignes d'écriture
		this.deleteListLigneEcritureComptable(pEcritureComptable.getId());
		this.insertListLigneEcritureComptable(pEcritureComptable);
	}

	@Override
	public void deleteEcritureComptable(Integer pId) {
		// ===== Suppression des lignes d'écriture
		this.deleteListLigneEcritureComptable(pId);

		// ===== Suppression de l'écriture
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("id", pId);
		vJdbcTemplate.update(sqlDeleteEcritureComptable, vSqlParams);
	}

	/**
	 * Supprime les lignes d'écriture de l'écriture comptable d'id {@code pEcritureId}
	 *
	 * @param pEcritureId id de l'écriture comptable
	 */
	protected void deleteListLigneEcritureComptable(Integer pEcritureId) {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
		MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
		vSqlParams.addValue("ecriture_id", pEcritureId);
		vJdbcTemplate.update(sqlDeleteListLigneEcritureComptable, vSqlParams);
	}
}
