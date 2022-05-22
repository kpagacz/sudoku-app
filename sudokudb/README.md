# Introduction
The `migration.sql` file contains the initial migration of this application's database schema.
You can see the database schema [here]("../data-model/data-model.png").

# Executing the migration
The migration file is designed to be executed as a user with `root` access to the database.
It drops and recreates the tables of the model. It does not populate the tables with any data.