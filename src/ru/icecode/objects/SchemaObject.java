package ru.icecode.objects;

/**
 * Created by QQQ on 14.07.2014.
 */
public class SchemaObject extends DbObject
{
    private final User owner;

    public SchemaObject(User owner, String name)
    {
        super(name);
        this.owner = owner;
    }

    @Override
    public String toString()
    {
        return owner.getName() + "." + getName();
    }
}
