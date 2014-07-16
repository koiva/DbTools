package ru.icecode.objects;


/**
 * Created by QQQ on 14.07.2014.
 */
public class DbObject
{
    private final String name;
    protected String ddlScript = "";

    public DbObject(String name)
    {
        if (name == null)
            throw new IllegalArgumentException();

        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DbObject dbObject = (DbObject) o;

        if (!name.equals(dbObject.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }


    public String getDdlScript() {
        return ddlScript;
    }
}
