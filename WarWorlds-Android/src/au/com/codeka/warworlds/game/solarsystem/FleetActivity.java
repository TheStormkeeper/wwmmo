package au.com.codeka.warworlds.game.solarsystem;

import java.util.List;
import java.util.TreeMap;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import au.com.codeka.warworlds.BaseActivity;
import au.com.codeka.warworlds.R;
import au.com.codeka.warworlds.ctrl.FleetList;
import au.com.codeka.warworlds.game.FleetMergeDialog;
import au.com.codeka.warworlds.game.FleetMoveDialog;
import au.com.codeka.warworlds.game.FleetSplitDialog;
import au.com.codeka.warworlds.model.EmpireManager;
import au.com.codeka.warworlds.model.Fleet;
import au.com.codeka.warworlds.model.Star;
import au.com.codeka.warworlds.model.StarManager;

public class FleetActivity extends BaseActivity {
    private Star mStar;
    private FleetList mFleetList;

    public FleetActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // remove the title bar

        mFleetList = new FleetList(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                                   FrameLayout.LayoutParams.MATCH_PARENT);
        addContentView(mFleetList, lp);

        String starKey = getIntent().getExtras().getString("au.com.codeka.warworlds.StarKey");
        StarManager.getInstance().requestStar(this, starKey, false, new StarManager.StarFetchedHandler() {
            @Override
            public void onStarFetched(Star s) {
                mStar = s;

                TreeMap<String, Star> stars = new TreeMap<String, Star>();
                stars.put(mStar.getKey(), mStar);
                mFleetList.refresh(mStar.getFleets(), stars);

                String fleetKey = getIntent().getExtras().getString("au.com.codeka.warworlds.FleetKey", null);
                if (fleetKey != null) {
                    mFleetList.selectFleet(fleetKey);
                }
            }
        });

        mFleetList.setOnFleetActionListener(new FleetList.OnFleetActionListener() {
            @Override
            public void onFleetView(Star star, Fleet fleet) {
                // won't be called here...
            }

            @Override
            public void onFleetSplit(Star star, Fleet fleet) {
                FragmentManager fm = getSupportFragmentManager();
                FleetSplitDialog dialog = new FleetSplitDialog();
                dialog.setFleet(fleet);
                dialog.show(fm, "");
            }

            @Override
            public void onFleetMove(Star star, Fleet fleet) {
                FragmentManager fm = getSupportFragmentManager();
                FleetMoveDialog dialog = new FleetMoveDialog();
                dialog.setFleet(fleet);
                dialog.show(fm, "");
            }

            @Override
            public void onFleetMerge(Fleet fleet, List<Fleet> potentialFleets) {
                FragmentManager fm = getSupportFragmentManager();
                FleetMergeDialog dialog = new FleetMergeDialog();
                dialog.setup(fleet, potentialFleets);
                dialog.show(fm, "");
            }

            @Override
            public void onFleetStanceModified(Star star, Fleet fleet, Fleet.Stance newStance) {
                EmpireManager.getInstance().getEmpire().updateFleetStance(
                        FleetActivity.this, star, fleet, newStance);
            }
        });

        // no "View" button, because it doesn't make sense here...
        mFleetList.findViewById(R.id.view_btn).setVisibility(View.GONE);
    }
}
