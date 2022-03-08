#### I've choose [PostgeSQL database](https://www.postgresql.org/).

**Terminal commands to init and create first database:**

```shell 
brew install postgresql
initdb 
createdb <DATABASE NAME> --encoding=['utf8', ...]
psql <DATABASE NAME>
```

**Filling database with lecture examples:**
```shell
psql <DATABASE NAME> -f hw1.sql
```

