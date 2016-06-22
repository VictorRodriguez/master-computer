package comm.model;

import java.util.List;

import comm.control.dao.DAORecord;

/**
 * Created by ditorres on 23/11/13.
 */
public class Report {

    private List<Record> report;
//    private DAORecord transRecord = new DAORecord();

    /**
     * Generate report daily or weekly for records.
     * @param type
     * @return a list of records
     */
    public List<Record> getReport(String type) {

        if(type != null){
            if(type.equals("Daily")){
//                report = transRecord.getReportDaily();
                return report;
            }
            if(type.equals("Weekly")){
//                report = transRecord.getReportWeekly();
                return report;
            }
        }
        return report;
    }
}
