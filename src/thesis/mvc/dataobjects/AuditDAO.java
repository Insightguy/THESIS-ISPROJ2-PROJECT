package thesis.mvc.dataobjects;

import java.util.List;

import thesis.mvc.model.Audit;

public interface AuditDAO {
	public void addAudit ( Audit audit );
	public void deleteAudit ( int auditId );
	public void updateAudit ( Audit audit );
	public List<Audit> getAllAudit();
	public Audit getAuditbyId ( int auditId );
}