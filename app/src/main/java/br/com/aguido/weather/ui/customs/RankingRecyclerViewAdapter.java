package br.com.aguido.weather.ui.customs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.rey.material.widget.ImageView;

import java.util.List;

import br.com.aguido.weather.R;
import br.com.aguido.weather.model.mongo.ScoreResults;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RankingRecyclerViewAdapter extends RecyclerView.Adapter<RankingRecyclerViewAdapter.RecyclerViewViewHolder> {

    @BindView(R.id.position_number)
    TextView positionNumber;

    @BindView(R.id.system_name)
    public TextView systemName;

    @BindView(R.id.medal_icon)
    ImageView medalIcon;

    @BindView(R.id.system_icon)
    ImageView sysIcon;

    @BindView(R.id.card_rate)
    TextView cardRate;

    private Fragment fa;
    private List<ScoreResults> sr;
    private static ItemClickListener itemClickListener;

    public RankingRecyclerViewAdapter(Fragment fa, List<ScoreResults> srl){
        this.sr = srl;
        this.fa = fa;
    }

    @Override
    public RecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.fragment_list_ranking_adapter, parent,false);
        ButterKnife.bind(this, rowView);
        return new RecyclerViewViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(RankingRecyclerViewAdapter.RecyclerViewViewHolder holder, int position) {

        sysIcon.setBackgroundResource(sr.get(position).getSysIcon());

        if (position == 0){
            medalIcon.setBackgroundResource(R.drawable.ic_goldmedal);
        } else if (position == 1){
            medalIcon.setBackgroundResource(R.drawable.ic_silvermedal);
        } else if (position == 2){
            medalIcon.setBackgroundResource(R.drawable.ic_bronzemedal);
        } else {
            medalIcon.setBackgroundResource(0);
        }

        String positionNum = position+1+fa.getString(R.string.sym_number_o);

        cardRate.setText(String.valueOf(sr.get(position).getScore()));
        positionNumber.setText(positionNum);
        systemName.setText(sr.get(position).getApplicationName());
        
    }

    @Override
    public int getItemCount() {
        return sr.size();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RecyclerViewViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener != null) {
                itemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        RankingRecyclerViewAdapter.itemClickListener = itemClickListener;
    }

}