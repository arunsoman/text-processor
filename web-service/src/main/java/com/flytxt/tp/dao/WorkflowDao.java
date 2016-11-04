package com.flytxt.tp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.flytxt.tp.EntityMetaData;
import com.flytxt.tp.EntityMetaData.KeyValue;
import com.flytxt.tp.NeonMeta;

@Repository
public class WorkflowDao {

	@Autowired
	private JdbcTemplate template;

	public List<NeonMeta> retrieveNeonMeta() {
		Map<Long, KeyValue> retrieveDkConfig = retrieveDkConfig();
		String profile = "SELECT o.ENTITY_ID entity_id,a.PARTNER_PROFILE_FIELD_ID attribute_id, a.PARTNER_PROFILE_FIELD_NAME entity_name, p.partner_id partner_id,  p.PARTNER_NAME partner_name,o.ENTITY_TYPE_ID entity_type_id, a.opt_type opt_type, a.agg_type agg_type FROM oz_entity o,  app_partner_profile_field a, oz_entity_attribute oa,  partner p WHERE o.external_reference_id = a.partner_id  AND a.partner_profile_field_id = oa.attribute_name  AND a.partner_id = p.partner_id";

		List<NeonMeta> neonMetaDetails = new ArrayList<>();

		setNeonMetaForProfileData(profile, neonMetaDetails, EntityMetaData.getkeyValueObject(1l, "Profile"), retrieveDkConfig);

		String list = "select B.ENTITY_ID entity_id,B.entity_name entity_name, c.partner_id partner_id, c.partner_name partner_name,B.ENTITY_TYPE_ID entity_type_id, a.opt_type opt_type, a.agg_type agg_type from oz_entity B, registration_list a , partner c where a.REGISTRATION_LIST_ID=B.EXTERNAL_REFERENCE_ID and B.ENTITY_TYPE_ID=3 and a.PARTNER_ID = c.partner_id";

		setNeonMeta(list, neonMetaDetails, EntityMetaData.getkeyValueObject(3l, "List"), retrieveDkConfig);
		
		String event = "select B.ENTITY_ID entity_id ,B.entity_name entity_name, c.partner_id partner_id,c.partner_name partner_name,B.ENTITY_TYPE_ID entity_type_id ,A.opt_type opt_type, A.agg_type agg_type from  oz_entity B, track_event_type A, partner c where A.EVENT_TYPE_ID=B.EXTERNAL_REFERENCE_ID and B.ENTITY_TYPE_ID=4 and ifnull(A.PARTNER_ID,-1) = c.partner_id";
		setNeonMeta(event, neonMetaDetails, EntityMetaData.getkeyValueObject(4l, "Event"), retrieveDkConfig);
		
		String metric = "select B.ENTITY_ID entity_id,B.entity_name entity_name, c.partner_id partner_id, c.partner_name partner_name,B.ENTITY_TYPE_ID entity_type_id ,a.opt_type opt_type ,a.agg_type agg_type from metric_config a, oz_entity B , partner c where a.METRIC_ID=B.EXTERNAL_REFERENCE_ID and B.ENTITY_TYPE_ID=2 and a.PARTNER_ID = c.partner_id";
		setNeonMeta(metric, neonMetaDetails, EntityMetaData.getkeyValueObject(2l, "Metric"), retrieveDkConfig);
		return neonMetaDetails;
	}

	private Map<Long, KeyValue> retrieveDkConfig() {

		return template.query("select id,value from dk_config", new ResultSetExtractor<Map<Long, KeyValue>>() {

			@Override
			public Map<Long, KeyValue> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Long, KeyValue> map = new HashMap<>();
				while (rs.next()) {
					KeyValue getkeyValueObject = EntityMetaData.getkeyValueObject(rs.getLong(1), rs.getString(2));
					map.put(getkeyValueObject.getId(), getkeyValueObject);

				}
				return map.size() > 0 ? map : null;
			}

		});

	}

	private void setNeonMetaForProfileData(String query, List<NeonMeta> neonMetaData, KeyValue type,
			Map<Long, KeyValue> dkConfig) {

		template.query(query, new ResultSetExtractor<List<NeonMeta>>() {

			@Override
			public List<NeonMeta> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					Long partnerID = rs.getLong("partner_id");
					NeonMeta neonMata = findPartnerInList(neonMetaData, partnerID);
					if (neonMata == null) {
						neonMata = new NeonMeta(partnerID, rs.getString("partner_name"), new ArrayList<>());
						neonMetaData.add(neonMata);
					}
					List<EntityMetaData> entityMeta = neonMata.getData();
					String opTypeStr = rs.getString("opt_type");
					String aggTypeStr = rs.getString("agg_type");
					List<KeyValue> opType = retrieveFromDkConfig(dkConfig, opTypeStr);
					List<KeyValue> aggType = retrieveFromDkConfig(dkConfig, aggTypeStr);
					entityMeta.add(new EntityMetaData(rs.getLong("entity_id"), rs.getString("entity_name"),
							rs.getLong("attribute_id"), type, opType, aggType));
				}
				return neonMetaData;
			}

		});
	}

	private void setNeonMeta(String query, List<NeonMeta> neonMetaData, KeyValue type, Map<Long, KeyValue> dkConfig) {

		template.query(query, new ResultSetExtractor<List<NeonMeta>>() {

			@Override
			public List<NeonMeta> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					Long partnerID = rs.getLong("partner_id");
					NeonMeta neonMata = findPartnerInList(neonMetaData, partnerID);
					if (neonMata == null) {
						neonMata = new NeonMeta(partnerID, rs.getString("partner_name"), new ArrayList<>());

					}
					List<EntityMetaData> entityMeta = neonMata.getData();
					String opTypeStr = rs.getString("opt_type");
					String aggTypeStr = rs.getString("agg_type");
					List<KeyValue> opType = retrieveFromDkConfig(dkConfig, opTypeStr);
					List<KeyValue> aggType = retrieveFromDkConfig(dkConfig, aggTypeStr);
					entityMeta.add(new EntityMetaData(rs.getLong("entity_id"), rs.getString("entity_name"), null, type,
							opType, aggType));
				}
				return neonMetaData;
			}

		});
	}

	private List<KeyValue> retrieveFromDkConfig(Map<Long, KeyValue> dkConfig, String opTypeStr) {
		if (opTypeStr != null) {
			List<KeyValue> opType = new ArrayList<>();
			String[] split = opTypeStr.split(",");
			for (String s : split) {
				KeyValue keyValue = dkConfig.get(Long.parseLong(s));
				if (keyValue != null)
					opType.add(keyValue);

			}
			return opType;
		}
		return null;
	}

	private NeonMeta findPartnerInList(List<NeonMeta> neonMetaData, Long partnerId) {
		Optional<NeonMeta> findFirst = neonMetaData.stream().filter(consumer -> consumer.getId().equals(partnerId))
				.findFirst();
		if (findFirst.isPresent())
			return findFirst.get();
		return null;
	}

}
