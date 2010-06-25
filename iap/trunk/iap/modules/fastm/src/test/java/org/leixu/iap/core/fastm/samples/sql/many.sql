<!-- BEGIN DYNAMIC: sql1 -->
select * from user_table1 
where 
dempartment_id = {text:user.departmentId}
and age > {param:ageLimit}
// BEGIN DYNAMIC: user.name.firstName
and first_name = {param:user.name.firstName}
// END DYNAMIC: user.name.firstName
// BEGIN DYNAMIC: user.name.lastName
and last_name = '{text:user.name.lastName}'
// END DYNAMIC: user.name.lastName
<!--  END DYNAMIC: sql1 -->

<!-- BEGIN DYNAMIC: sql2 -->
select * from user_table2 
where 
dempartment_id = {text:user.departmentId}
and age > {param:ageLimit}
<!-- END DYNAMIC: sql2 -->

<!-- BEGIN DYNAMIC: sql3 -->
select * from user_table3 
where 
dempartment_id = {text:user.departmentId}
// BEGIN DYNAMIC: user.name.firstName
and first_name = {param:user.name.firstName}
// END DYNAMIC: user.name.firstName
// BEGIN DYNAMIC: user.name.lastName
and last_name = '{text:user.name.lastName}'
// END DYNAMIC: user.name.lastName
<!-- END DYNAMIC: sql3 -->
