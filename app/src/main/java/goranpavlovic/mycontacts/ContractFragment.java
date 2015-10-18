package goranpavlovic.mycontacts;

import android.app.Fragment;
import android.content.Context;

public class ContractFragment<T> extends Fragment
{
    private T mContract;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try
        {
            mContract = (T) getActivity();
        }
        catch (ClassCastException e)
        {
            throw new IllegalStateException("Activity does not implement fragment contract!");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();

        mContract = null;
    }

    public T getContract()
    {
        return mContract;
    }
}
