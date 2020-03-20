package com.dummy.myerp.consumer.db;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.EnumMap;
import java.util.Map;


/**
 * <p>Classe mère des classes de Consumer DB</p>
 */
public abstract class AbstractDbConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDbConsumer.class);

	private static Map<DataSourcesEnum, DataSource> mapDataSource;

	protected AbstractDbConsumer() {
		super();
	}

	protected static DaoProxy getDaoProxy() {
		return ConsumerHelper.getDaoProxy();
	}

	/**
	 * Méthode de configuration de la classe.
	 * On pilote l'ajout avec l'Enum et on ne rajoute pas tout à l'aveuglette...
	 *
	 * @param pEnumMapDataSource -
	 */
	public static void configure(Map<DataSourcesEnum, DataSource> pEnumMapDataSource) {
		mapDataSource = new EnumMap<>(DataSourcesEnum.class);
		for (DataSourcesEnum dataSourceId : DataSourcesEnum.values()) {
			DataSource vDataSource = pEnumMapDataSource.get(dataSourceId);
			// On teste si la DataSource est configurée
			// (NB : elle est considérée comme non configurée si elle est dans pEnumMapDataSource mais à null)
			if (vDataSource == null) {
				if (pEnumMapDataSource.containsKey(dataSourceId)) {
					LOGGER.error("La DataSource {} n'a pas été configurée. Elle est ignorée.", dataSourceId);
				} else {
					LOGGER.warn("La DataSource {} n'est pas trouvée dans la liste.", dataSourceId);
				}
			} else {
				mapDataSource.put(dataSourceId, vDataSource);
			}
		}
	}

	protected DataSource getDataSource(DataSourcesEnum pDataSourceId) {
		if (mapDataSource == null) {
			throw new UnsatisfiedLinkError("La configuration des DataSources n'a pas été réalisée.");
		}
		DataSource vRetour = mapDataSource.get(pDataSourceId);
		if (vRetour == null) {
			throw new UnsatisfiedLinkError("La DataSource suivante n'a pas été initialisée : " + pDataSourceId);
		}
		return vRetour;
	}

	/**
	 * Renvoie le dernière valeur utilisé d'une séquence
	 *
	 * <p><i><b>Attention : </b>Méthode spécifique au SGBD PostgreSQL</i></p>
	 *
	 * @param <T>            : La classe de la valeur de la séquence.
	 * @param pDataSourcesId : L'identifiant de la {@link DataSource} à utiliser
	 * @param pSeqName       : Le nom de la séquence dont on veut récupérer la valeur
	 * @param pSeqValueClass : Classe de la valeur de la séquence
	 * @return la dernière valeur de la séquence
	 */
	protected <T> T queryGetSequenceValuePostgreSQL(DataSourcesEnum pDataSourcesId,
	                                                String pSeqName, Class<T> pSeqValueClass) {
		JdbcTemplate vJdbcTemplate = new JdbcTemplate(getDataSource(pDataSourcesId));
		String vSeqSQL = "SELECT last_value FROM " + pSeqName;
		return vJdbcTemplate.queryForObject(vSeqSQL, pSeqValueClass);
	}
}
