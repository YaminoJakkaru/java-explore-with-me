# java-explore-with-me
Template repository for ExploreWithMe project.
# ссылка на pull request:
https://github.com/YaminoJakkaru/java-explore-with-me/actions/runs/5835162509/job/15826220438?pr=9
# описание особенности:
Добавлена возможность оставлять комментарии под собыиями.
<br/>
Комментарии можно добавить, так же коментарии можно редактировать, удалять и реагировать (положительно или отрицательно)
на них.
<br/>
Комментарии можно получить по отдельному публиному эндпоинту, модно получить или все комментарии к событию по id 
события, или отдельный комментарий по непосредственно его id, для это создан класс PublicCommentController.
Для остальных фунцкий создан класс PrivateCommentController.
<br/>
Для работы с данными созданы пакеты comment и reaction со стандарнтыми наборами классов 
(dto, service, repository, model)
<br/>
Так же добалены 2 таблици в файл schema.sql: comment и reaction.
<br/>
Таблица comment содержит следующие поля 
id, note максимальной varchar длинной 512 символов - хранит сообщение, edited BOOLEAN показывает был ли комментарий 
отредактирован, event_id и author_id.
<br/>
Таблица reaction содержит следующие поля id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, comment_id, responder_id        
positive BOOLEAN - показывает, была ли реакция положительной
<br/>
На всех уровнях работы с данными присутствуют стандартные проверки
