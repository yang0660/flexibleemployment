package com.businesstrace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {
    public static void main(String[] args) {
//                "; Duplicate entry 'LILE001' for key 'uniq_idx_contract_number'; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry 'LILE001' for key 'uniq_idx_contract_number'").matches();
        Matcher matcher = Pattern.compile("^.*Duplicate entry '(.*)' for key '(.*)'.*$", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher("### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry 'LILE001' for key 'uniq_idx_contract_number'\n" +
                "### The error may involve com.flexibleemployment.dao.mapper.ContractMapper.insert-Inline\n" +
                "### The error occurred while setting parameters\n" +
                "### SQL: insert into t_contract (id, contract_name, contract_number,        category_id, remark, `status`,        sub_status, create_time, create_user,        update_time, update_user, company_id,        user_type, attachment_url, origin_attachment_url,        delete_status, archived_time, archived_user_id,        deadline_look_time)     values (?, ?, ?,        ?, ?, ?,        ?, ?, ?,        ?, ?, ?,        ?, ?, ?,        ?, ?, ?,        ?)\n" +
                "### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Duplicate entry 'LILE001' for key 'uniq_idx_contract_number'\n");
        matcher.matches();
        System.out.println(matcher.group(1));
    }
}
