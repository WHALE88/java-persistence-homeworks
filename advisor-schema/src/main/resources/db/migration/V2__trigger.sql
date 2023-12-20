-- This trigger function is designed to automatically set the 'assigned_at' field
-- to the current timestamp when a new advisor is assigned, but only if 'assigned_at'
-- is currently null and 'advisor_id' is not null.
CREATE OR REPLACE FUNCTION update_assigned_at()
    RETURNS TRIGGER AS $$
BEGIN
    -- Check if advisor_id is not null and assigned_at is null
    IF NEW.advisor_id IS NOT NULL AND NEW.assigned_at IS NULL THEN
        NEW.assigned_at := now();
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER update_assigned_at_trigger
    BEFORE INSERT OR UPDATE ON applications
    FOR EACH ROW EXECUTE FUNCTION update_assigned_at();

-- This function checks if the 'id' of a new advisor being inserted into the 'advisors'
-- table already exists in the 'applicants' table. If it does, an exception is raised,
-- preventing the insertion. This enforces a rule that the same ID cannot be present in
-- both 'applicants' and 'advisors' tables.
CREATE OR REPLACE FUNCTION check_advisor_id()
    RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM advisory_schema.applicants WHERE id = NEW.id) THEN
        RAISE EXCEPTION 'ID already exists in applicants';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_advisor
    BEFORE INSERT ON advisors
    FOR EACH ROW EXECUTE FUNCTION check_advisor_id();

-- Similar to 'check_advisor_id', this function checks if the 'id' of a new applicant
-- being inserted into the 'applicants' table already exists in the 'advisors' table.
-- An exception is raised if such a duplicate ID is found, maintaining ID uniqueness
-- between these two tables.
CREATE OR REPLACE FUNCTION check_applicant_id()
    RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (SELECT 1 FROM advisory_schema.advisors WHERE id = NEW.id) THEN
        RAISE EXCEPTION 'ID already exists in advisors';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_applicant
    BEFORE INSERT ON applicants
    FOR EACH ROW EXECUTE FUNCTION check_applicant_id();