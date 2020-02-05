package br.com.aguido.weather.ui.customs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.aguido.weather.R;
import br.com.aguido.weather.model.mongo.ScoreResults;
import br.com.aguido.weather.ui.scores.SystemScoreFragment;
import br.com.aguido.weather.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class CardSystemAdapter extends RecyclerView.Adapter<CardSystemAdapter.MyViewHolder> {

    @BindView(R.id.thumbnail)
    ImageView thumb;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.card_view)
    CardView cv;

    private FragmentActivity mContext;
    private List<ScoreResults> srl;

    class MyViewHolder extends RecyclerView.ViewHolder {
        MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(v -> {
                Log.i("CLICKED ON CARD SYSTEM ", srl.get(this.getLayoutPosition()).getApplicationName());

                FragmentTransaction transaction = mContext.getSupportFragmentManager().beginTransaction();
                Bundle b = new Bundle();
                b.putString(Constants.SharedPreferences.SYSTEM_NAME, srl.get(this.getLayoutPosition()).getApplicationName());

                SystemScoreFragment ns = new SystemScoreFragment();
                ns.setArguments(b);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                transaction.replace(R.id.content_frame, ns, "notas");
                transaction.commitAllowingStateLoss();
            });
        }
    }

    public CardSystemAdapter(FragmentActivity mContext, List<ScoreResults> srl) {
        this.mContext = mContext;
        this.srl = srl;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.card_system_adapter, parent,false);
        ButterKnife.bind(this, rowView);
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        thumb.setImageResource(srl.get(position).getSysIcon());
        title.setText(srl.get(position).getApplicationName());
    }

    @Override
    public int getItemCount() {
        return srl.size();
    }

}
