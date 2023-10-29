-- Query to get the list of users who took the training lesson more than once at the same day,
-- grouped by user and training lesson, each ordered from the most recent lesson date to oldest date.

select ut.user_id, ut.username, td.training_id, td.training_date, count(td.training_date)
from user_table ut
join training_details td using(user_id)
group by ut.user_id, ut.username, td.training_id, td.training_date
having count(td.training_date) > 1;