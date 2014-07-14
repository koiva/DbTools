package ru.icecode.objects;

/**
 * Created by w on 14.07.2014.
 */
public class Trigger
{
    private final String name;
    private final User owner;

    public Trigger(User owner, String name)
    {
        this.owner = owner;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return owner.getName() + "." + name;
    }
}
