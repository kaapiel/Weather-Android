package br.com.cielo.weather.ui.scores;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Comparator;

import br.com.cielo.weather.R;
import br.com.cielo.weather.appManagement.WeatherFragment;
import br.com.cielo.weather.model.mongo.ScoreResults;
import br.com.cielo.weather.model.numbers.OverallNumbers;
import br.com.cielo.weather.ui.customs.CardSystemAdapter;
import br.com.cielo.weather.ui.home.HomeActivity;
import br.com.cielo.weather.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class OverallScoreFragment extends WeatherFragment {

    @BindView(R.id.album_recyclerview)
    RecyclerView rv;

    @BindView(R.id.lines_overall)
    TextView linesOverall;

    @BindView(R.id.issues_overall)
    TextView issuesOverall;

    private ScoreResults[] apps;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.overall_score_fragment, container, false);
        ButterKnife.bind(this, view);

        setupNumbers();
        fullScreenCall();

        CardSystemAdapter sa = new CardSystemAdapter(this.getActivity(), getApplicationList(apps));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(20), false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(sa);

        return view;
    }

    @OnClick(R.id.open_drawer)
    void onMenuClicked() {
        ((HomeActivity) getActivity()).openDrawer();
    }

    private void setupNumbers() {

        apps = new Gson()
                .fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.LAST_OK_APPLICATIONS, Constants.SharedPreferences.LAST_OK_APPLICATIONS), ScoreResults[].class);

        Arrays.sort(apps, (Comparator<Object>) (o1, o2) ->
                Float.compare(((ScoreResults) o2).getScore(),
                        ((ScoreResults) o1).getScore()));

        OverallNumbers on = getNumbers(apps);

        startCountAnimation(linesOverall,
                1000,
                Float.valueOf(removeLastChar(on.getOverallLines())),
                validateUnit(on.getOverallLines()),
                true);

        startCountAnimation(issuesOverall,
                1800,
                Float.valueOf(removeLastChar(on.getOverallIssues())),
                validateUnit(on.getOverallIssues()),
                true);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
